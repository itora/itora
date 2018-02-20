package com.github.itora.chain;

import com.github.itora.tx.Tx;

public final class ChainLink extends Chain {

    public final Chain previous;

    public final Tx tx;

    public ChainLink(Chain previous, Tx tx) {
        this.previous = previous;
        this.tx = tx;
    }

    public interface Factory {

        public static ChainLink chainLink(Chain previous, Tx tx) {
            return new ChainLink(previous, tx);
        }
    }

    @Override
    final <R> R visit(Chain.Visitor<R> visitor) {
        return visitor.visitChainLink(this);
    }

    public final Chain previous() {
        return previous;
    }

    public final ChainLink withPrevious(Chain previous) {
        return new ChainLink(previous, tx);
    }

    public final Tx tx() {
        return tx;
    }

    public final ChainLink withTx(Tx tx) {
        return new ChainLink(previous, tx);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return ChainLink.builder().previous(previous).tx(tx);
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
        ChainLink that = (ChainLink) o;
        return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.tx, that.tx);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(previous, tx);
    }

    @Override
    public final String toString() {
        return "ChainLink{previous = " + this.previous + ", tx = " + this.tx + "}";
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

        public final ChainLink build() {
            return new ChainLink(previous, tx);
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
            ChainLink.Builder that = (ChainLink.Builder) o;
            return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.tx, that.tx);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(previous, tx);
        }

        @Override
        public final String toString() {
            return "ChainLink.Builder{previous = " + this.previous + ", tx = " + this.tx + "}";
        }
    }
}
