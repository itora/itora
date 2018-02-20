package com.github.itora.account.internal;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.github.itora.account.Account;
import com.github.itora.account.AccountManager;
import com.github.itora.account.internal.BlockValiditation.Status;
import com.github.itora.amount.Amount;
import com.github.itora.amount.Amounts;
import com.github.itora.bootstrap.LatticeBootstrap;
import com.github.itora.chain.Chain;
import com.github.itora.chain.Chains;
import com.github.itora.chain.Lattice;
import com.github.itora.chain.Lattices;
import com.github.itora.event.Event;
import com.github.itora.event.OpenEvent;
import com.github.itora.event.ReceiveEvent;
import com.github.itora.event.SendEvent;
import com.github.itora.tx.OpenTx;
import com.github.itora.tx.ReceiveTx;
import com.github.itora.tx.SendTx;
import com.github.itora.tx.Tx;
import com.github.itora.tx.TxId;
import com.github.itora.tx.TxIds;

public final class AccountManagerImpl implements AccountManager {

    private final AtomicReference<Lattice> lattice = new AtomicReference<>(Lattices.EMPTY);

    public AccountManagerImpl(LatticeBootstrap latticeBootstrap) {
        lattice.set(latticeBootstrap.bootstrap());
    }

    public Amount balance(Account account) {
        return balance(account, lattice.get());
    }

    public static Amount balance(Account account, Lattice lattice) {
        Amount sum = Amounts.ZERO;
        for (Tx tx : Chains.iterate(lattice.chains.get(account))) {
            sum = Amounts.plus(sum, Tx.visit(tx, new Tx.Visitor<Amount>() {
                @Override
                public Amount visitOpenTx(OpenTx tx) {
                    return Amounts.ZERO;
                }

                @Override
                public Amount visitReceiveTx(ReceiveTx tx) {
                    return tx.amount();
                }

                @Override
                public Amount visitSendTx(SendTx tx) {
                    return Amounts.invers(tx.amount());
                }
            }));
        }
        return sum;
    }

    @Override
    public void accept(Event event) {
        lattice.getAndUpdate(currentLattice -> accept(event, currentLattice));
    }

    public static Lattice accept(Event event, Lattice lattice) {
        // TODO Check the event validity
        return Event.visit(event, new Event.Visitor<Lattice>() {

            @Override
            public Lattice visitOpenEvent(OpenEvent event) {
                TxId txId = TxIds.txId(event);

                // Check if this block was already processed!
                Block thisBlock = findBlock(lattice, txId);
                if (thisBlock != null) {
                    return lattice;
                }

                Account account = event.account();
                Chain previous = lattice.chains.get(account);

                // A chain must be inexistent or the open event is ignored
                if (previous != null) {
                    return lattice;
                }

                Tx tx = Tx.Factory.openTx(txId, event.timestamp());

                Chain chain = Chain.Factory.chainLink(Chains.ROOT, tx);

                return lattice.withChains(lattice.chains().put(account, chain));
            }

            @Override
            public Lattice visitReceiveEvent(ReceiveEvent event) {
                BlockValiditation previousBlockValidation = checkBlockValidity(lattice, event.previous);

                if (previousBlockValidation.status != Status.ACCEPTED) {
                    System.out.println("Block was rejected with cause: " + previousBlockValidation.status);
                }
                Block previousBlock = previousBlockValidation.block;
                TxId txId = TxIds.txId(event);

                TxId sourceTxId = event.source();

                Amount amount = findAmount(lattice, sourceTxId);

                Tx tx = Tx.Factory.receiveTx(txId, amount, event.timestamp());
                Chain previous = lattice.chains.get(previousBlock.account);

                return add(previousBlock.account, previous, tx);
            }

            @Override
            public Lattice visitSendEvent(SendEvent event) {
                BlockValiditation previousBlockValidation = checkBlockValidity(lattice, event.previous);

                if (previousBlockValidation.status != Status.ACCEPTED) {
                    System.out.println("Block was rejected with cause: " + previousBlockValidation.status);
                }

                TxId txId = TxIds.txId(event);

                Account account = event.from();

                Tx tx = Tx.Factory.sendTx(txId, event.to(), event.amount(), event.timestamp());
                Chain previous = lattice.chains.get(account);
                return add(account, previous, tx);

            }

            private Lattice add(Account account, Chain previous, Tx tx) {
                Chain chain = Chain.Factory.chainLink(previous, tx);

                return lattice.withChains(lattice.chains().put(account, chain));
            }
        });
    }

    private static Amount findAmount(Lattice lattice, TxId sourceTxId) {
        Amount amount = null;
        for (java.util.Map.Entry<Account, Chain> entry : lattice.chains.asMap().entrySet()) {
            for (Tx tx : Chains.iterate(entry.getValue())) {
                if (sourceTxId.equals(tx.txId())) {
                    Optional<Amount> a = Tx.visit(tx, new Tx.Visitor<Optional<Amount>>() {
                        @Override
                        public Optional<Amount> visitOpenTx(OpenTx tx) {
                            return Optional.empty();
                        }

                        @Override
                        public Optional<Amount> visitReceiveTx(ReceiveTx tx) {
                            return Optional.empty();
                        }

                        @Override
                        public Optional<Amount> visitSendTx(SendTx tx) {
                            return Optional.of(tx.amount());
                        }
                    });

                    amount = a.get();
                    break;
                }
            }
            if (amount != null) {
                break;
            }
        }
        return amount;
    }

    private static BlockValiditation checkBlockValidity(Lattice lattice, TxId previous) {
        Block previousBlock = findBlock(lattice, previous);

        if (previousBlock == null) {
            // Dangling block?!
            return BlockValiditation.DANGLING;
        }

        if (!Chains.isHead(previousBlock.txId, previousBlock.chain)) {
            return BlockValiditation.forked(previousBlock);
        }
        return BlockValiditation.accepted(previousBlock);
    }

    private static Block findBlock(Lattice lattice, TxId txId) {
        Tx foundTx = null;
        Account foundAccount = null;
        Chain foundChain = null;
        for (java.util.Map.Entry<Account, Chain> entry : lattice.chains.asMap().entrySet()) {
            Chain chain = entry.getValue();
            for (Tx tx : Chains.iterate(chain)) {
                if (txId.equals(tx.txId())) {
                    foundAccount = entry.getKey();
                    foundTx = tx;
                    foundChain = chain;
                    // We could also get back to the OpenTx
                    break;
                }
            }
            if (foundAccount != null) {
                break;
            }
        }
        return foundTx == null ? null : new Block(foundAccount, foundChain, foundTx, txId);
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