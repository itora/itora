package com.github.itora.request;

import com.github.itora.tx.AccountTxId;
import java.time.Instant;
import com.github.itora.tx.TxId;

public final class ReceiveRequest extends Request {

    public final TxId previous;

    public final AccountTxId source;

    public final long pow;

    public final Instant timestamp;

    public ReceiveRequest(TxId previous, AccountTxId source, long pow, Instant timestamp) {
        this.previous = previous;
        this.source = source;
        this.pow = pow;
        this.timestamp = timestamp;
    }

    public interface Factory {

        public static ReceiveRequest receiveRequest(TxId previous, AccountTxId source, long pow, Instant timestamp) {
            return new ReceiveRequest(previous, source, pow, timestamp);
        }
    }

    @Override
    final <R> R visit(Request.Visitor<R> visitor) {
        return visitor.visitReceiveRequest(this);
    }

    public final TxId previous() {
        return previous;
    }

    public final ReceiveRequest withPrevious(TxId previous) {
        return new ReceiveRequest(previous, source, pow, timestamp);
    }

    public final AccountTxId source() {
        return source;
    }

    public final ReceiveRequest withSource(AccountTxId source) {
        return new ReceiveRequest(previous, source, pow, timestamp);
    }

    @Override
    public final long pow() {
        return pow;
    }

    @Override
    public final ReceiveRequest withPow(long pow) {
        return new ReceiveRequest(previous, source, pow, timestamp);
    }

    @Override
    public final Instant timestamp() {
        return timestamp;
    }

    @Override
    public final ReceiveRequest withTimestamp(Instant timestamp) {
        return new ReceiveRequest(previous, source, pow, timestamp);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return ReceiveRequest.builder().previous(previous).source(source).pow(pow).timestamp(timestamp);
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
        ReceiveRequest that = (ReceiveRequest) o;
        return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.source, that.source) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(previous, source, pow, timestamp);
    }

    @Override
    public final String toString() {
        return "ReceiveRequest{previous = " + this.previous + ", source = " + this.source + ", pow = " + this.pow + ", timestamp = " + this.timestamp + "}";
    }

    public static final class Builder {

        public TxId previous;

        public AccountTxId source;

        public long pow;

        public Instant timestamp;

        public final TxId previous() {
            return previous;
        }

        public final Builder previous(TxId previous) {
            this.previous = previous;
            return this;
        }

        public final AccountTxId source() {
            return source;
        }

        public final Builder source(AccountTxId source) {
            this.source = source;
            return this;
        }

        public final long pow() {
            return pow;
        }

        public final Builder pow(long pow) {
            this.pow = pow;
            return this;
        }

        public final Instant timestamp() {
            return timestamp;
        }

        public final Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public final ReceiveRequest build() {
            return new ReceiveRequest(previous, source, pow, timestamp);
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
            ReceiveRequest.Builder that = (ReceiveRequest.Builder) o;
            return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.source, that.source) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(previous, source, pow, timestamp);
        }

        @Override
        public final String toString() {
            return "ReceiveRequest.Builder{previous = " + this.previous + ", source = " + this.source + ", pow = " + this.pow + ", timestamp = " + this.timestamp + "}";
        }
    }
}
