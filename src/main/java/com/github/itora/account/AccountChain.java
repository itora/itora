package com.github.itora.account;

import com.github.itora.amount.Amount;
import com.github.itora.chain.Chain;

public final class AccountChain {

    public final Chain chain;

    public final Amount balance;

    public AccountChain(Chain chain, Amount balance) {
        this.chain = chain;
        this.balance = balance;
    }

    public interface Factory {

        public static AccountChain accountChain(Chain chain, Amount balance) {
            return new AccountChain(chain, balance);
        }
    }

    public final Chain chain() {
        return chain;
    }

    public final AccountChain withChain(Chain chain) {
        return new AccountChain(chain, balance);
    }

    public final Amount balance() {
        return balance;
    }

    public final AccountChain withBalance(Amount balance) {
        return new AccountChain(chain, balance);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return AccountChain.builder().chain(chain).balance(balance);
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
        AccountChain that = (AccountChain) o;
        return java.util.Objects.equals(this.chain, that.chain) && java.util.Objects.equals(this.balance, that.balance);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(chain, balance);
    }

    @Override
    public final String toString() {
        return "AccountChain{chain = " + this.chain + ", balance = " + this.balance + "}";
    }

    public static final class Builder {

        public Chain chain;

        public Amount balance;

        public final Chain chain() {
            return chain;
        }

        public final Builder chain(Chain chain) {
            this.chain = chain;
            return this;
        }

        public final Amount balance() {
            return balance;
        }

        public final Builder balance(Amount balance) {
            this.balance = balance;
            return this;
        }

        public final AccountChain build() {
            return new AccountChain(chain, balance);
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
            AccountChain.Builder that = (AccountChain.Builder) o;
            return java.util.Objects.equals(this.chain, that.chain) && java.util.Objects.equals(this.balance, that.balance);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(chain, balance);
        }

        @Override
        public final String toString() {
            return "AccountChain.Builder{chain = " + this.chain + ", balance = " + this.balance + "}";
        }
    }
}
