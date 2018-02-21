package com.github.itora.request;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import java.time.Instant;
import com.github.itora.tx.TxId;

public final class SendRequest extends Request {

    public final TxId previous;

    public final Account from;

    public final Account to;

    public final Amount amount;

    public final long pow;

    public final Instant timestamp;

    public SendRequest(TxId previous, Account from, Account to, Amount amount, long pow, Instant timestamp) {
        this.previous = previous;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.pow = pow;
        this.timestamp = timestamp;
    }

    public interface Factory {

        public static SendRequest sendRequest(TxId previous, Account from, Account to, Amount amount, long pow, Instant timestamp) {
            return new SendRequest(previous, from, to, amount, pow, timestamp);
        }
    }

    @Override
    final <R> R visit(Request.Visitor<R> visitor) {
        return visitor.visitSendRequest(this);
    }

    public final TxId previous() {
        return previous;
    }

    public final SendRequest withPrevious(TxId previous) {
        return new SendRequest(previous, from, to, amount, pow, timestamp);
    }

    public final Account from() {
        return from;
    }

    public final SendRequest withFrom(Account from) {
        return new SendRequest(previous, from, to, amount, pow, timestamp);
    }

    public final Account to() {
        return to;
    }

    public final SendRequest withTo(Account to) {
        return new SendRequest(previous, from, to, amount, pow, timestamp);
    }

    public final Amount amount() {
        return amount;
    }

    public final SendRequest withAmount(Amount amount) {
        return new SendRequest(previous, from, to, amount, pow, timestamp);
    }

    @Override
    public final long pow() {
        return pow;
    }

    @Override
    public final SendRequest withPow(long pow) {
        return new SendRequest(previous, from, to, amount, pow, timestamp);
    }

    @Override
    public final Instant timestamp() {
        return timestamp;
    }

    @Override
    public final SendRequest withTimestamp(Instant timestamp) {
        return new SendRequest(previous, from, to, amount, pow, timestamp);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return SendRequest.builder().previous(previous).from(from).to(to).amount(amount).pow(pow).timestamp(timestamp);
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
        SendRequest that = (SendRequest) o;
        return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.from, that.from) && java.util.Objects.equals(this.to, that.to) && java.util.Objects.equals(this.amount, that.amount) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(previous, from, to, amount, pow, timestamp);
    }

    @Override
    public final String toString() {
        return "SendRequest{previous = " + this.previous + ", from = " + this.from + ", to = " + this.to + ", amount = " + this.amount + ", pow = " + this.pow + ", timestamp = " + this.timestamp + "}";
    }

    public static final class Builder {

        public TxId previous;

        public Account from;

        public Account to;

        public Amount amount;

        public long pow;

        public Instant timestamp;

        public final TxId previous() {
            return previous;
        }

        public final Builder previous(TxId previous) {
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

        public final SendRequest build() {
            return new SendRequest(previous, from, to, amount, pow, timestamp);
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
            SendRequest.Builder that = (SendRequest.Builder) o;
            return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.from, that.from) && java.util.Objects.equals(this.to, that.to) && java.util.Objects.equals(this.amount, that.amount) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(previous, from, to, amount, pow, timestamp);
        }

        @Override
        public final String toString() {
            return "SendRequest.Builder{previous = " + this.previous + ", from = " + this.from + ", to = " + this.to + ", amount = " + this.amount + ", pow = " + this.pow + ", timestamp = " + this.timestamp + "}";
        }
    }
}
