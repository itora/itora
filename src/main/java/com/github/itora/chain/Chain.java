package com.github.itora.chain;

import com.github.itora.tx.Tx;

public final class Chain {

    public final Chain previous;

    public final Tx tx;

    public Chain(Chain previous, Tx tx) {
        this.previous = previous;
        this.tx = tx;
    }

    public interface Factory {

        public static Chain chain(Chain previous, Tx tx) {
            return new Chain(previous, tx);
        }
    }

    public final Chain previous() {
        return previous;
    }

    public final Chain withPrevious(Chain previous) {
        return new Chain(previous, tx);
    }

    public final Tx tx() {
        return tx;
    }

    public final Chain withTx(Tx tx) {
        return new Chain(previous, tx);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return Chain.builder().previous(previous).tx(tx);
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
        Chain that = (Chain) o;
        return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.tx, that.tx);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(previous, tx);
    }

    @Override
    public final String toString() {
        return "Chain{previous = " + this.previous + ", tx = " + this.tx + "}";
    }

    public static final class Builder {

        public Chain previous;

        public Tx tx;

        public final Chain previous() {
            return previous;
        }

        public final Builder previous(Chain previous) {
            this.previous = previous;
            return this;
        }

        public final Tx tx() {
            return tx;
        }

        public final Builder tx(Tx tx) {
            this.tx = tx;
            return this;
        }

        public final Chain build() {
            return new Chain(previous, tx);
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
            Chain.Builder that = (Chain.Builder) o;
            return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.tx, that.tx);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(previous, tx);
        }

        @Override
        public final String toString() {
            return "Chain.Builder{previous = " + this.previous + ", tx = " + this.tx + "}";
        }
    }
}
