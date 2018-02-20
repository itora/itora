package com.github.itora.tx;

import com.github.itora.amount.Amount;
import java.time.Instant;

public final class ReceiveTx extends Tx {

    public final TxId txId;

    public final Amount amount;

    public final Instant timestamp;

    public ReceiveTx(TxId txId, Amount amount, Instant timestamp) {
        this.txId = txId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public interface Factory {

        public static ReceiveTx receiveTx(TxId txId, Amount amount, Instant timestamp) {
            return new ReceiveTx(txId, amount, timestamp);
        }
    }

    @Override
    final <R> R visit(Tx.Visitor<R> visitor) {
        return visitor.visitReceiveTx(this);
    }

    @Override
    public final TxId txId() {
        return txId;
    }

    @Override
    public final ReceiveTx withTxId(TxId txId) {
        return new ReceiveTx(txId, amount, timestamp);
    }

    public final Amount amount() {
        return amount;
    }

    public final ReceiveTx withAmount(Amount amount) {
        return new ReceiveTx(txId, amount, timestamp);
    }

    @Override
    public final Instant timestamp() {
        return timestamp;
    }

    @Override
    public final ReceiveTx withTimestamp(Instant timestamp) {
        return new ReceiveTx(txId, amount, timestamp);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return ReceiveTx.builder().txId(txId).amount(amount).timestamp(timestamp);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        ReceiveTx that = (ReceiveTx) o;
        return java.util.Objects.equals(this.txId, that.txId) && java.util.Objects.equals(this.amount, that.amount) && java.util.Objects.equals(this.timestamp, that.timestamp);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(txId, amount, timestamp);
    }

    @Override
    public final String toString() {
        return "ReceiveTx{txId = " + this.txId + ", amount = " + this.amount + ", timestamp = " + this.timestamp + "}";
    }

    public static final class Builder {

        public TxId txId;

        public Amount amount;

        public Instant timestamp;

        public final TxId txId() {
            return txId;
        }

        public final Builder txId(TxId txId) {
            this.txId = txId;
            return this;
        }

        public final Amount amount() {
            return amount;
        }

        public final Builder amount(Amount amount) {
            this.amount = amount;
            return this;
        }

        public final Instant timestamp() {
            return timestamp;
        }

        public final Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public final ReceiveTx build() {
            return new ReceiveTx(txId, amount, timestamp);
        }

        @Override
        public final boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null) {
                return false;
            }
            if (getClass() != o.getClass()) {
                return false;
            }
            ReceiveTx.Builder that = (ReceiveTx.Builder) o;
            return java.util.Objects.equals(this.txId, that.txId) && java.util.Objects.equals(this.amount, that.amount) && java.util.Objects.equals(this.timestamp, that.timestamp);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(txId, amount, timestamp);
        }

        @Override
        public final String toString() {
            return "ReceiveTx.Builder{txId = " + this.txId + ", amount = " + this.amount + ", timestamp = " + this.timestamp + "}";
        }
    }
}
