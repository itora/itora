package com.github.itora.event;

import com.github.itora.account.Account;
import java.time.Instant;

public final class OpenEvent extends Event {

    public final Account account;

    public final long pow;

    public final Instant timestamp;

    public OpenEvent(Account account, long pow, Instant timestamp) {
        this.account = account;
        this.pow = pow;
        this.timestamp = timestamp;
    }

    public interface Factory {

        public static OpenEvent openEvent(Account account, long pow, Instant timestamp) {
            return new OpenEvent(account, pow, timestamp);
        }
    }

    @Override
    final <R> R visit(Event.Visitor<R> visitor) {
        return visitor.visitOpenEvent(this);
    }

    public final Account account() {
        return account;
    }

    public final OpenEvent withAccount(Account account) {
        return new OpenEvent(account, pow, timestamp);
    }

    @Override
    public final long pow() {
        return pow;
    }

    @Override
    public final OpenEvent withPow(long pow) {
        return new OpenEvent(account, pow, timestamp);
    }

    @Override
    public final Instant timestamp() {
        return timestamp;
    }

    @Override
    public final OpenEvent withTimestamp(Instant timestamp) {
        return new OpenEvent(account, pow, timestamp);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return OpenEvent.builder().account(account).pow(pow).timestamp(timestamp);
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
        OpenEvent that = (OpenEvent) o;
        return java.util.Objects.equals(this.account, that.account) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(account, pow, timestamp);
    }

    @Override
    public final String toString() {
        return "OpenEvent{account = " + this.account + ", pow = " + this.pow + ", timestamp = " + this.timestamp + "}";
    }

    public static final class Builder {

        public Account account;

        public long pow;

        public Instant timestamp;

        public final Account account() {
            return account;
        }

        public final Builder account(Account account) {
            this.account = account;
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

        public final OpenEvent build() {
            return new OpenEvent(account, pow, timestamp);
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
            OpenEvent.Builder that = (OpenEvent.Builder) o;
            return java.util.Objects.equals(this.account, that.account) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(account, pow, timestamp);
        }

        @Override
        public final String toString() {
            return "OpenEvent.Builder{account = " + this.account + ", pow = " + this.pow + ", timestamp = " + this.timestamp + "}";
        }
    }
}
