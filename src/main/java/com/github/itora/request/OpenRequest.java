package com.github.itora.request;

import com.github.itora.account.Account;
import java.time.Instant;

public final class OpenRequest extends Request {

    public final Account account;

    public final long pow;

    public final Instant timestamp;

    public OpenRequest(Account account, long pow, Instant timestamp) {
        this.account = account;
        this.pow = pow;
        this.timestamp = timestamp;
    }

    public interface Factory {

        public static OpenRequest openRequest(Account account, long pow, Instant timestamp) {
            return new OpenRequest(account, pow, timestamp);
        }
    }

    @Override
    final <R> R visit(Request.Visitor<R> visitor) {
        return visitor.visitOpenRequest(this);
    }

    public final Account account() {
        return account;
    }

    public final OpenRequest withAccount(Account account) {
        return new OpenRequest(account, pow, timestamp);
    }

    @Override
    public final long pow() {
        return pow;
    }

    @Override
    public final OpenRequest withPow(long pow) {
        return new OpenRequest(account, pow, timestamp);
    }

    @Override
    public final Instant timestamp() {
        return timestamp;
    }

    @Override
    public final OpenRequest withTimestamp(Instant timestamp) {
        return new OpenRequest(account, pow, timestamp);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return OpenRequest.builder().account(account).pow(pow).timestamp(timestamp);
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
        OpenRequest that = (OpenRequest) o;
        return java.util.Objects.equals(this.account, that.account) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(account, pow, timestamp);
    }

    @Override
    public final String toString() {
        return "OpenRequest{account = " + this.account + ", pow = " + this.pow + ", timestamp = " + this.timestamp + "}";
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

        public final OpenRequest build() {
            return new OpenRequest(account, pow, timestamp);
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
            OpenRequest.Builder that = (OpenRequest.Builder) o;
            return java.util.Objects.equals(this.account, that.account) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(account, pow, timestamp);
        }

        @Override
        public final String toString() {
            return "OpenRequest.Builder{account = " + this.account + ", pow = " + this.pow + ", timestamp = " + this.timestamp + "}";
        }
    }
}
