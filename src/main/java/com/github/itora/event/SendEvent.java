package com.github.itora.event;

import com.github.itora.account.Account;
import com.github.itora.account.Amount;
import java.time.Instant;

public final class SendEvent extends Event {

    public final BlockId previous;

    public final Account from;

    public final Account to;

    public final Amount amount;

    public final long pow;

    public final Instant timestamp;

    public final long signature;

    public SendEvent(BlockId previous, Account from, Account to, Amount amount, long pow, Instant timestamp, long signature) {
        this.previous = previous;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.pow = pow;
        this.timestamp = timestamp;
        this.signature = signature;
    }

    public interface Factory {

        public static SendEvent sendEvent(BlockId previous, Account from, Account to, Amount amount, long pow, Instant timestamp, long signature) {
            return new SendEvent(previous, from, to, amount, pow, timestamp, signature);
        }
    }

    @Override
    final <R> R visit(Event.Visitor<R> visitor) {
        return visitor.visitSendEvent(this);
    }

    public final BlockId previous() {
        return previous;
    }

    public final SendEvent withPrevious(BlockId previous) {
        return new SendEvent(previous, from, to, amount, pow, timestamp, signature);
    }

    public final Account from() {
        return from;
    }

    public final SendEvent withFrom(Account from) {
        return new SendEvent(previous, from, to, amount, pow, timestamp, signature);
    }

    public final Account to() {
        return to;
    }

    public final SendEvent withTo(Account to) {
        return new SendEvent(previous, from, to, amount, pow, timestamp, signature);
    }

    public final Amount amount() {
        return amount;
    }

    public final SendEvent withAmount(Amount amount) {
        return new SendEvent(previous, from, to, amount, pow, timestamp, signature);
    }

    @Override
    public final long pow() {
        return pow;
    }

    @Override
    public final SendEvent withPow(long pow) {
        return new SendEvent(previous, from, to, amount, pow, timestamp, signature);
    }

    @Override
    public final Instant timestamp() {
        return timestamp;
    }

    @Override
    public final SendEvent withTimestamp(Instant timestamp) {
        return new SendEvent(previous, from, to, amount, pow, timestamp, signature);
    }

    @Override
    public final long signature() {
        return signature;
    }

    @Override
    public final SendEvent withSignature(long signature) {
        return new SendEvent(previous, from, to, amount, pow, timestamp, signature);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return SendEvent.builder().previous(previous).from(from).to(to).amount(amount).pow(pow).timestamp(timestamp).signature(signature);
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
        SendEvent that = (SendEvent) o;
        return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.from, that.from) && java.util.Objects.equals(this.to, that.to) && java.util.Objects.equals(this.amount, that.amount) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp) && java.util.Objects.equals(this.signature, that.signature);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(previous, from, to, amount, pow, timestamp, signature);
    }

    @Override
    public final String toString() {
        return "SendEvent{previous = " + this.previous + ", from = " + this.from + ", to = " + this.to + ", amount = " + this.amount + ", pow = " + this.pow + ", timestamp = " + this.timestamp + ", signature = " + this.signature + "}";
    }

    public static final class Builder {

        public BlockId previous;

        public Account from;

        public Account to;

        public Amount amount;

        public long pow;

        public Instant timestamp;

        public long signature;

        public final BlockId previous() {
            return previous;
        }

        public final Builder previous(BlockId previous) {
            this.previous = previous;
            return this;
        }

        public final Account from() {
            return from;
        }

        public final Builder from(Account from) {
            this.from = from;
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

        public final SendEvent build() {
            return new SendEvent(previous, from, to, amount, pow, timestamp, signature);
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
            SendEvent.Builder that = (SendEvent.Builder) o;
            return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.from, that.from) && java.util.Objects.equals(this.to, that.to) && java.util.Objects.equals(this.amount, that.amount) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp) && java.util.Objects.equals(this.signature, that.signature);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(previous, from, to, amount, pow, timestamp, signature);
        }

        @Override
        public final String toString() {
            return "SendEvent.Builder{previous = " + this.previous + ", from = " + this.from + ", to = " + this.to + ", amount = " + this.amount + ", pow = " + this.pow + ", timestamp = " + this.timestamp + ", signature = " + this.signature + "}";
        }
    }
}
