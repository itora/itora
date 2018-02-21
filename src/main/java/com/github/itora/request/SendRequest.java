package com.github.itora.request;

import com.github.itora.account.Account;
import com.github.itora.tx.AccountTxId;
import com.github.itora.amount.Amount;
import java.time.Instant;

public final class SendRequest extends Request {

    public final AccountTxId previous;

    public final Account destination;

    public final Amount amount;

    public final Instant timestamp;

    public SendRequest(AccountTxId previous, Account destination, Amount amount, Instant timestamp) {
        this.previous = previous;
        this.destination = destination;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public interface Factory {

        public static SendRequest sendRequest(AccountTxId previous, Account destination, Amount amount, Instant timestamp) {
            return new SendRequest(previous, destination, amount, timestamp);
        }
    }

    @Override
    final <R> R visit(Request.Visitor<R> visitor) {
        return visitor.visitSendRequest(this);
    }

    public final AccountTxId previous() {
        return previous;
    }

    public final SendRequest withPrevious(AccountTxId previous) {
        return new SendRequest(previous, destination, amount, timestamp);
    }

    public final Account destination() {
        return destination;
    }

    public final SendRequest withDestination(Account destination) {
        return new SendRequest(previous, destination, amount, timestamp);
    }

    public final Amount amount() {
        return amount;
    }

    public final SendRequest withAmount(Amount amount) {
        return new SendRequest(previous, destination, amount, timestamp);
    }

    @Override
    public final Instant timestamp() {
        return timestamp;
    }

    @Override
    public final SendRequest withTimestamp(Instant timestamp) {
        return new SendRequest(previous, destination, amount, timestamp);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return SendRequest.builder().previous(previous).destination(destination).amount(amount).timestamp(timestamp);
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
        return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.destination, that.destination) && java.util.Objects.equals(this.amount, that.amount) && java.util.Objects.equals(this.timestamp, that.timestamp);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(previous, destination, amount, timestamp);
    }

    @Override
    public final String toString() {
        return "SendRequest{previous = " + this.previous + ", destination = " + this.destination + ", amount = " + this.amount + ", timestamp = " + this.timestamp + "}";
    }

    public static final class Builder {

        public AccountTxId previous;

        public Account destination;

        public Amount amount;

        public Instant timestamp;

        public final AccountTxId previous() {
            return previous;
        }

        public final Builder previous(AccountTxId previous) {
            this.previous = previous;
            return this;
        }

        public final Account destination() {
            return destination;
        }

        public final Builder destination(Account destination) {
            this.destination = destination;
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

        public final SendRequest build() {
            return new SendRequest(previous, destination, amount, timestamp);
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
            return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.destination, that.destination) && java.util.Objects.equals(this.amount, that.amount) && java.util.Objects.equals(this.timestamp, that.timestamp);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(previous, destination, amount, timestamp);
        }

        @Override
        public final String toString() {
            return "SendRequest.Builder{previous = " + this.previous + ", destination = " + this.destination + ", amount = " + this.amount + ", timestamp = " + this.timestamp + "}";
        }
    }
}
