package com.github.itora.account.internal;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

import com.github.itora.account.Account;
import com.github.itora.account.AccountChain;
import com.github.itora.account.AccountManager;
import com.github.itora.account.Accounts;
import com.github.itora.account.internal.BlockValiditation.Status;
import com.github.itora.amount.Amount;
import com.github.itora.amount.Amounts;
import com.github.itora.bootstrap.LatticeBootstrap;
import com.github.itora.chain.Chain;
import com.github.itora.chain.Chains;
import com.github.itora.request.OpenRequest;
import com.github.itora.request.ReceiveRequest;
import com.github.itora.request.Request;
import com.github.itora.request.Requests;
import com.github.itora.request.SendRequest;
import com.github.itora.tx.AccountTxId;
import com.github.itora.tx.OpenTx;
import com.github.itora.tx.ReceiveTx;
import com.github.itora.tx.SendTx;
import com.github.itora.tx.Tx;
import com.github.itora.tx.TxId;
import com.github.itora.tx.TxIds;

public final class AccountManagerImpl implements AccountManager {

    private final ConcurrentMap<Account, AccountChain> lattice;

    private final static Tx.Visitor<Optional<Amount>> SENT_AMOUNT_VISITOR = new Tx.Visitor<Optional<Amount>>() {

        @Override
        public Optional<Amount> visitOpenTx(OpenTx openTx) {
            return Optional.empty();
        }

        @Override
        public Optional<Amount> visitSendTx(SendTx sendTx) {
            return Optional.of(sendTx.amount);
        }

        @Override
        public Optional<Amount> visitReceiveTx(ReceiveTx receiveTx) {
            return Optional.empty();
        }

    };

    public AccountManagerImpl(LatticeBootstrap latticeBootstrap) {
        lattice = new ConcurrentHashMap<>(latticeBootstrap.bootstrap().chains().asMap());
    }

    public Amount balance(Account account) {
        AccountChain accountChain = lattice.get(account);
        if (accountChain != null) {
            return accountChain.balance;
        }
        return Amounts.ZERO;
    }

    @Override
    public void accept(Request request) {
        Account account = Requests.emitter(request);
        lattice.compute(account, (k, v) -> accept(request, account, v, lattice::get));
    }

    public static AccountChain accept(Request request, Account account, AccountChain accountChain,
            Function<Account, AccountChain> accountToChain) {
        // TODO Check the request validity
        return Request.visit(request, new Request.Visitor<AccountChain>() {

            @Override
            public AccountChain visitOpenRequest(OpenRequest request) {
                TxId txId = TxIds.txId(request);

                // A chain must be inexistent or the open request is ignored
                if (accountChain != null) {
                    return accountChain;
                }

                Tx tx = Tx.Factory.openTx(txId, request.timestamp());

                Chain chain = Chain.Factory.chainLink(Chains.ROOT, tx);
                return AccountChain.Factory.accountChain(chain, Amounts.ZERO);
            }

            @Override
            public AccountChain visitSendRequest(SendRequest request) {
                BlockValiditation previousBlockValidation = checkBlockValidity(accountChain.chain, request.previous);

                if (previousBlockValidation.status != Status.ACCEPTED) {
                    System.out.println("Block was rejected with cause: " + previousBlockValidation.status);
                    return accountChain;
                }

                // Check balance!

                Amount newBalance = Amounts.minus(accountChain.balance, request.amount());

                if (Amounts.isStrictlyNegative(newBalance) && !Accounts.EX_NIHILO.equals(account)) {
                    return accountChain; // reject send, lacking funds
                }

                TxId txId = TxIds.txId(request);

                Tx tx = Tx.Factory.sendTx(txId, request.destination(), request.amount(), request.timestamp());

                Chain chain = Chain.Factory.chainLink(accountChain.chain, tx);
                AccountChain accountChain = AccountChain.Factory.accountChain(chain, newBalance);

                return accountChain;
            }

            @Override
            public AccountChain visitReceiveRequest(ReceiveRequest request) {
                BlockValiditation previousBlockValidation = checkBlockValidity(accountChain.chain, request.previous);

                if (previousBlockValidation.status != Status.ACCEPTED) {
                    System.out.println("Block was rejected with cause: " + previousBlockValidation.status);
                    return accountChain;
                }

                TxId txId = TxIds.txId(request);

                AccountTxId source = request.source();

                Optional<Tx> mbTx = findTx(source, accountToChain);

                Amount sentAmount = mbTx.flatMap(tx -> Tx.visit(tx, SENT_AMOUNT_VISITOR)).orElse(null);

                if (sentAmount == null) {
                    // TODO buffer request!
                    return accountChain;
                }

                Tx tx = Tx.Factory.receiveTx(txId, sentAmount, request.timestamp());

                Chain chain = Chain.Factory.chainLink(accountChain.chain, tx);
                AccountChain updatedAccountChain = AccountChain.Factory.accountChain(chain, Amounts.plus(accountChain.balance, sentAmount));

                return updatedAccountChain;
            }

        });
    }

    private static Optional<Tx> findTx(AccountTxId accountTxId, Function<Account, AccountChain> accountToChain) {
        AccountChain accountChain = accountToChain.apply(accountTxId.account);
        if (accountChain != null) {
            Chain chain = accountChain.chain;
            for (Tx tx : Chains.iterate(chain)) {
                if (accountTxId.txId().equals(tx.txId())) {
                    return Optional.of(tx);
                }
            }
        }
        return Optional.empty();
    }

    private static BlockValiditation checkBlockValidity(Chain chain, AccountTxId previous) {
        // TODO handle block de-duplication
        Block previousBlock = findBlock(chain, previous);

        if (previousBlock == null) {
            // Dangling block?!
            return BlockValiditation.DANGLING;
        }

        if (!Chains.isHead(previousBlock.txId, previousBlock.chain)) {
            return BlockValiditation.forked(previousBlock);
        }
        return BlockValiditation.accepted(previousBlock);
    }

    private static Block findBlock(Chain chain, AccountTxId previous) {
        Account account = previous.account();
        for (Tx tx : Chains.iterate(chain)) {
            if (previous.txId.equals(tx.txId())) {
                return new Block(account, chain, tx, previous.txId());
            }
        }
        return null;
    }

}

final class Block {
    public final Account account;
    public final Chain chain;
    public final Tx tx;
    public final TxId txId;

    public Block(Account account, Chain chain, Tx tx, TxId txId) {
        this.account = account;
        this.chain = chain;
        this.tx = tx;
        this.txId = txId;
    }
}

final class BlockValiditation {
    public static final BlockValiditation DANGLING = new BlockValiditation(Status.DANGLING, null);

    public enum Status {
        ACCEPTED, DANGLING, FORKED
    }

    public final Status status;
    public final Block block;

    public BlockValiditation(Status status, Block block) {
        this.status = status;
        this.block = block;
    }

    public static BlockValiditation dangling() {
        return DANGLING;
    }

    public static BlockValiditation forked(Block block) {
        return new BlockValiditation(Status.FORKED, block);
    }

    public static BlockValiditation accepted(Block block) {
        return new BlockValiditation(Status.ACCEPTED, block);
    }

}