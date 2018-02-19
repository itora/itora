package com.github.itora.tx;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import java.time.Instant;

public final class SendTx extends Tx {

    public final TxId txId;

    public final Account to;

    public final Amount amount;

    public final Instant timestamp;

    public SendTx(TxId txId, Account to, Amount amount, Instant timestamp) {
        this.txId = txId;
        this.to = to;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public interface Factory {

        public static SendTx sendTx(TxId txId, Account to, Amount amount, Instant timestamp) {
            return new SendTx(txId, to, amount, timestamp);
        }
    }

    @Override
    final <R> R visit(Tx.Visitor<R> visitor) {
        return visitor.visitSendTx(this);
    }

    @Override
    public final TxId txId() {
        return txId;
    }

    @Override
    public final SendTx withTxId(TxId txId) {
        return new SendTx(txId, to, amount, timestamp);
    }

    public final Account to() {
        return to;
    }

    public final SendTx withTo(Account to) {
        return new SendTx(txId, to, amount, timestamp);
    }

    public final Amount amount() {
        return amount;
    }

    public final SendTx withAmount(Amount amount) {
        return new SendTx(txId, to, amount, timestamp);
    }

    @Override
    public final Instant timestamp() {
        return timestamp;
    }

    @Override
    public final SendTx withTimestamp(Instant timestamp) {
        return new SendTx(txId, to, amount, timestamp);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return SendTx.builder().txId(txId).to(to).amount(amount).timestamp(timestamp);
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
        SendTx that = (SendTx) o;
        return java.util.Objects.equals(this.txId, that.txId) && java.util.Objects.equals(this.to, that.to) && java.util.Objects.equals(this.amount, that.amount) && java.util.Objects.equals(this.timestamp, that.timestamp);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(txId, to, amount, timestamp);
    }

    @Override
    public final String toString() {
        return "SendTx{txId = " + this.txId + ", to = " + this.to + ", amount = " + this.amount + ", timestamp = " + this.timestamp + "}";
    }

    public static final class Builder {

        public TxId txId;

        public Account to;

        public Amount amount;

        public Instant timestamp;

        public final TxId txId() {
            return txId;
        }

        public final Builder txId(TxId txId) {
            this.txId = txId;
            return this;
        }

        public final Account to() {
            return to;
        }

        public final Builder to(Account to) {
            this.to = to;
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

        public final SendTx build() {
            return new SendTx(txId, to, amount, timestamp);
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
            SendTx.Builder that = (SendTx.Builder) o;
            return java.util.Objects.equals(this.txId, that.txId) && java.util.Objects.equals(this.to, that.to) && java.util.Objects.equals(this.amount, that.amount) && java.util.Objects.equals(this.timestamp, that.timestamp);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(txId, to, amount, timestamp);
        }

        @Override
        public final String toString() {
            return "SendTx.Builder{txId = " + this.txId + ", to = " + this.to + ", amount = " + this.amount + ", timestamp = " + this.timestamp + "}";
        }
    }
}
