package com.github.itora.account;

import com.github.itora.crypto.PublicKey;

public final class Account {

    public final PublicKey key;

    public Account(PublicKey key) {
        this.key = key;
    }

    public interface Factory {

        public static Account account(PublicKey key) {
            return new Account(key);
        }
    }

    public final PublicKey key() {
        return key;
    }

    public final Account withKey(PublicKey key) {
        return new Account(key);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return Account.builder().key(key);
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
        return java.util.Objects.equals(this.key, that.key);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(key);
    }

    @Override
    public final String toString() {
        return "Account{key = " + this.key + "}";
    }

    public static final class Builder {

        public PublicKey key;

        public final PublicKey key() {
            return key;
        }

        public final Builder key(PublicKey key) {
            this.key = key;
            return this;
        }

        public final Account build() {
            return new Account(key);
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
            return java.util.Objects.equals(this.key, that.key);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(key);
        }

        @Override
        public final String toString() {
            return "Account.Builder{key = " + this.key + "}";
        }
    }
}
