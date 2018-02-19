package com.github.itora.event;

import com.github.itora.account.Account;
import java.time.Instant;

public final class ReceiveEvent extends Event {

    public final long previous;

    public final Account source;

    public final Account to;

    public final long amount;

    public final long pow;

    public final Instant timestamp;

    public final long signature;

    public ReceiveEvent(long previous, Account source, Account to, long amount, long pow, Instant timestamp, long signature) {
        this.previous = previous;
        this.source = source;
        this.to = to;
        this.amount = amount;
        this.pow = pow;
        this.timestamp = timestamp;
        this.signature = signature;
    }

    public interface Factory {

        public static ReceiveEvent receiveEvent(long previous, Account source, Account to, long amount, long pow, Instant timestamp, long signature) {
            return new ReceiveEvent(previous, source, to, amount, pow, timestamp, signature);
        }
    }

    @Override
    final <R> R visit(Event.Visitor<R> visitor) {
        return visitor.visitReceiveEvent(this);
    }

    public final long previous() {
        return previous;
    }

    public final ReceiveEvent withPrevious(long previous) {
        return new ReceiveEvent(previous, source, to, amount, pow, timestamp, signature);
    }

    public final Account source() {
        return source;
    }

    public final ReceiveEvent withSource(Account source) {
        return new ReceiveEvent(previous, source, to, amount, pow, timestamp, signature);
    }

    public final Account to() {
        return to;
    }

    public final ReceiveEvent withTo(Account to) {
        return new ReceiveEvent(previous, source, to, amount, pow, timestamp, signature);
    }

    public final long amount() {
        return amount;
    }

    public final ReceiveEvent withAmount(long amount) {
        return new ReceiveEvent(previous, source, to, amount, pow, timestamp, signature);
    }

    @Override
    public final long pow() {
        return pow;
    }

    @Override
    public final ReceiveEvent withPow(long pow) {
        return new ReceiveEvent(previous, source, to, amount, pow, timestamp, signature);
    }

    @Override
    public final Instant timestamp() {
        return timestamp;
    }

    @Override
    public final ReceiveEvent withTimestamp(Instant timestamp) {
        return new ReceiveEvent(previous, source, to, amount, pow, timestamp, signature);
    }

    @Override
    public final long signature() {
        return signature;
    }

    @Override
    public final ReceiveEvent withSignature(long signature) {
        return new ReceiveEvent(previous, source, to, amount, pow, timestamp, signature);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return ReceiveEvent.builder().previous(previous).source(source).to(to).amount(amount).pow(pow).timestamp(timestamp).signature(signature);
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
        ReceiveEvent that = (ReceiveEvent) o;
        return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.source, that.source) && java.util.Objects.equals(this.to, that.to) && java.util.Objects.equals(this.amount, that.amount) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp) && java.util.Objects.equals(this.signature, that.signature);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(previous, source, to, amount, pow, timestamp, signature);
    }

    @Override
    public final String toString() {
        return "ReceiveEvent{previous = " + this.previous + ", source = " + this.source + ", to = " + this.to + ", amount = " + this.amount + ", pow = " + this.pow + ", timestamp = " + this.timestamp + ", signature = " + this.signature + "}";
    }

    public static final class Builder {

        public long previous;

        public Account source;

        public Account to;

        public long amount;

        public long pow;

        public Instant timestamp;

        public long signature;

        public final long previous() {
            return previous;
        }

        public final Builder previous(long previous) {
            this.previous = previous;
            return this;
        }

        public final Account source() {
            return source;
        }

        public final Builder source(Account source) {
            this.source = source;
            return this;
        }

        public final Account to() {
            return to;
        }

        public final Builder to(Account to) {
            this.to = to;
            return this;
        }

        public final long amount() {
            return amount;
        }

        public final Builder amount(long amount) {
            this.amount = amount;
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

        public final long signature() {
            return signature;
        }

        public final Builder signature(long signature) {
            this.signature = signature;
            return this;
        }

        public final ReceiveEvent build() {
            return new ReceiveEvent(previous, source, to, amount, pow, timestamp, signature);
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
            ReceiveEvent.Builder that = (ReceiveEvent.Builder) o;
            return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.source, that.source) && java.util.Objects.equals(this.to, that.to) && java.util.Objects.equals(this.amount, that.amount) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp) && java.util.Objects.equals(this.signature, that.signature);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(previous, source, to, amount, pow, timestamp, signature);
        }

        @Override
        public final String toString() {
            return "ReceiveEvent.Builder{previous = " + this.previous + ", source = " + this.source + ", to = " + this.to + ", amount = " + this.amount + ", pow = " + this.pow + ", timestamp = " + this.timestamp + ", signature = " + this.signature + "}";
        }
    }
}
