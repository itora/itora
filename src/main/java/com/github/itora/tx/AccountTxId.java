package com.github.itora.tx;

import com.github.itora.account.Account;

public final class AccountTxId {

    public final Account account;

    public final TxId txId;

    public AccountTxId(Account account, TxId txId) {
        this.account = account;
        this.txId = txId;
    }

    public interface Factory {

        public static AccountTxId accountTxId(Account account, TxId txId) {
            return new AccountTxId(account, txId);
        }
    }

    public final Account account() {
        return account;
    }

    public final AccountTxId withAccount(Account account) {
        return new AccountTxId(account, txId);
    }

    public final TxId txId() {
        return txId;
    }

    public final AccountTxId withTxId(TxId txId) {
        return new AccountTxId(account, txId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return AccountTxId.builder().account(account).txId(txId);
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
        AccountTxId that = (AccountTxId) o;
        return java.util.Objects.equals(this.account, that.account) && java.util.Objects.equals(this.txId, that.txId);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(account, txId);
    }

    @Override
    public final String toString() {
        return "AccountTxId{account = " + this.account + ", txId = " + this.txId + "}";
    }

    public static final class Builder {

        public Account account;

        public TxId txId;

        public final Account account() {
            return account;
        }

        public final Builder account(Account account) {
            this.account = account;
            return this;
        }

        public final TxId txId() {
            return txId;
        }

        public final Builder txId(TxId txId) {
            this.txId = txId;
            return this;
        }

        public final AccountTxId build() {
            return new AccountTxId(account, txId);
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
            AccountTxId.Builder that = (AccountTxId.Builder) o;
            return java.util.Objects.equals(this.account, that.account) && java.util.Objects.equals(this.txId, that.txId);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(account, txId);
        }

        @Override
        public final String toString() {
            return "AccountTxId.Builder{account = " + this.account + ", txId = " + this.txId + "}";
        }
    }
}
