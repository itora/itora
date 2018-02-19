package com.github.itora.tx;

import java.time.Instant;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;

public abstract class Tx {

    Tx() {
    }

    public interface Factory {

        public static OpenTx openTx(TxId txId, Instant timestamp) {
            return new OpenTx(txId, timestamp);
        }

        public static SendTx sendTx(TxId txId, Account to, Amount amount, Instant timestamp) {
            return new SendTx(txId, to, amount, timestamp);
        }

        public static ReceiveTx receiveTx(TxId txId, Amount amount, Instant timestamp) {
            return new ReceiveTx(txId, amount, timestamp);
        }
    }

    public static <R> R visit(Tx tx, Tx.Visitor<R> visitor) {
        return tx.visit(visitor);
    }

    abstract <R> R visit(Tx.Visitor<R> visitor);

    public abstract TxId txId();

    public abstract Tx withTxId(TxId txId);

    public abstract Instant timestamp();

    public abstract Tx withTimestamp(Instant timestamp);

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    public interface Visitor<R> {

        R visitOpenTx(OpenTx openTx);

        R visitSendTx(SendTx sendTx);

        R visitReceiveTx(ReceiveTx receiveTx);
    }
}
