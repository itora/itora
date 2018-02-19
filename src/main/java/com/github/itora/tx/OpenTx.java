package com.github.itora.tx;

import java.time.Instant;

public final class OpenTx extends Tx {

    public final Instant timestamp;

    public final TxId txId;

    public OpenTx(Instant timestamp, TxId txId) {
        this.timestamp = timestamp;
        this.txId = txId;
    }

    public interface Factory {

        public static OpenTx openTx(Instant timestamp, TxId txId) {
            return new OpenTx(timestamp, txId);
        }
    }

    @Override
    final <R> R visit(Tx.Visitor<R> visitor) {
        return visitor.visitOpenTx(this);
    }

    @Override
    public final Instant timestamp() {
        return timestamp;
    }

    @Override
    public final OpenTx withTimestamp(Instant timestamp) {
        return new OpenTx(timestamp, txId);
    }

    @Override
    public final TxId txId() {
        return txId;
    }

    @Override
    public final OpenTx withTxId(TxId txId) {
        return new OpenTx(timestamp, txId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return OpenTx.builder().timestamp(timestamp).txId(txId);
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
        OpenTx that = (OpenTx) o;
        return java.util.Objects.equals(this.timestamp, that.timestamp) && java.util.Objects.equals(this.txId, that.txId);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(timestamp, txId);
    }

    @Override
    public final String toString() {
        return "OpenTx{timestamp = " + this.timestamp + ", txId = " + this.txId + "}";
    }

    public static final class Builder {

        public Instant timestamp;

        public TxId txId;

        public final Instant timestamp() {
            return timestamp;
        }

        public final Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public final TxId txId() {
            return txId;
        }

        public final Builder txId(TxId txId) {
            this.txId = txId;
            return this;
        }

        public final OpenTx build() {
            return new OpenTx(timestamp, txId);
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
            OpenTx.Builder that = (OpenTx.Builder) o;
            return java.util.Objects.equals(this.timestamp, that.timestamp) && java.util.Objects.equals(this.txId, that.txId);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(timestamp, txId);
        }

        @Override
        public final String toString() {
            return "OpenTx.Builder{timestamp = " + this.timestamp + ", txId = " + this.txId + "}";
        }
    }
}
