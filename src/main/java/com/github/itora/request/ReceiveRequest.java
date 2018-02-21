package com.github.itora.request;

import com.github.itora.tx.AccountTxId;
import java.time.Instant;

public final class ReceiveRequest extends Request {

    public final AccountTxId previous;

    public final AccountTxId source;

    public final Instant timestamp;

    public ReceiveRequest(AccountTxId previous, AccountTxId source, Instant timestamp) {
        this.previous = previous;
        this.source = source;
        this.timestamp = timestamp;
    }

    public interface Factory {

        public static ReceiveRequest receiveRequest(AccountTxId previous, AccountTxId source, Instant timestamp) {
            return new ReceiveRequest(previous, source, timestamp);
        }
    }

    @Override
    final <R> R visit(Request.Visitor<R> visitor) {
        return visitor.visitReceiveRequest(this);
    }

    public final AccountTxId previous() {
        return previous;
    }

    public final ReceiveRequest withPrevious(AccountTxId previous) {
        return new ReceiveRequest(previous, source, timestamp);
    }

    public final AccountTxId source() {
        return source;
    }

    public final ReceiveRequest withSource(AccountTxId source) {
        return new ReceiveRequest(previous, source, timestamp);
    }

    @Override
    public final Instant timestamp() {
        return timestamp;
    }

    @Override
    public final ReceiveRequest withTimestamp(Instant timestamp) {
        return new ReceiveRequest(previous, source, timestamp);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return ReceiveRequest.builder().previous(previous).source(source).timestamp(timestamp);
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
        return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.source, that.source) && java.util.Objects.equals(this.timestamp, that.timestamp);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(previous, source, timestamp);
    }

    @Override
    public final String toString() {
        return "ReceiveRequest{previous = " + this.previous + ", source = " + this.source + ", timestamp = " + this.timestamp + "}";
    }

    public static final class Builder {

        public AccountTxId previous;

        public AccountTxId source;

        public Instant timestamp;

        public final AccountTxId previous() {
            return previous;
        }

        public final Builder previous(AccountTxId previous) {
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

        public final Instant timestamp() {
            return timestamp;
        }

        public final Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public final ReceiveRequest build() {
            return new ReceiveRequest(previous, source, timestamp);
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
            return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.source, that.source) && java.util.Objects.equals(this.timestamp, that.timestamp);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(previous, source, timestamp);
        }

        @Override
        public final String toString() {
            return "ReceiveRequest.Builder{previous = " + this.previous + ", source = " + this.source + ", timestamp = " + this.timestamp + "}";
        }
    }
}
