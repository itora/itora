package com.github.itora.tx;

import java.time.Instant;

public final class OpenTx extends Tx {

    public final TxId txId;

    public final Instant timestamp;

    public OpenTx(TxId txId, Instant timestamp) {
        this.txId = txId;
        this.timestamp = timestamp;
    }

    public interface Factory {

        public static OpenTx openTx(TxId txId, Instant timestamp) {
            return new OpenTx(txId, timestamp);
        }
    }

    @Override
    final <R> R visit(Tx.Visitor<R> visitor) {
        return visitor.visitOpenTx(this);
    }

    @Override
    public final TxId txId() {
        return txId;
    }

    @Override
    public final OpenTx withTxId(TxId txId) {
        return new OpenTx(txId, timestamp);
    }

    @Override
    public final Instant timestamp() {
        return timestamp;
    }

    @Override
    public final OpenTx withTimestamp(Instant timestamp) {
        return new OpenTx(txId, timestamp);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return OpenTx.builder().txId(txId).timestamp(timestamp);
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
        return java.util.Objects.equals(this.txId, that.txId) && java.util.Objects.equals(this.timestamp, that.timestamp);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(txId, timestamp);
    }

    @Override
    public final String toString() {
        return "OpenTx{txId = " + this.txId + ", timestamp = " + this.timestamp + "}";
    }

    public static final class Builder {

        public TxId txId;

        public Instant timestamp;

        public final TxId txId() {
            return txId;
        }

        public final Builder txId(TxId txId) {
            this.txId = txId;
            return this;
        }

        public final Instant timestamp() {
            return timestamp;
        }

        public final Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public final OpenTx build() {
            return new OpenTx(txId, timestamp);
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
            return java.util.Objects.equals(this.txId, that.txId) && java.util.Objects.equals(this.timestamp, that.timestamp);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(txId, timestamp);
        }

        @Override
        public final String toString() {
            return "OpenTx.Builder{txId = " + this.txId + ", timestamp = " + this.timestamp + "}";
        }
    }
}
