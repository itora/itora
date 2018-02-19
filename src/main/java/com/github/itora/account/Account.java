package com.github.itora.account;

public final class Account {

    public final long number;

    public Account(long number) {
        this.number = number;
    }

    public interface Factory {

        public static Account account(long number) {
            return new Account(number);
        }
    }

    public final long number() {
        return number;
    }

    public final Account withNumber(long number) {
        return new Account(number);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return Account.builder().number(number);
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
        Account that = (Account) o;
        return java.util.Objects.equals(this.number, that.number);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(number);
    }

    @Override
    public final String toString() {
        return "Account{number = " + this.number + "}";
    }

    public static final class Builder {

        public long number;

        public final long number() {
            return number;
        }

        public final Builder number(long number) {
            this.number = number;
            return this;
        }

        public final Account build() {
            return new Account(number);
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
            Account.Builder that = (Account.Builder) o;
            return java.util.Objects.equals(this.number, that.number);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(number);
        }

        @Override
        public final String toString() {
            return "Account.Builder{number = " + this.number + "}";
        }
    }
}
