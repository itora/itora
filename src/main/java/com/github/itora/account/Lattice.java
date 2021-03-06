package com.github.itora.account;

import com.github.andrewoma.dexx.collection.Map;

public final class Lattice {

    public final Map<Account, AccountChain> chains;

    public Lattice(Map<Account, AccountChain> chains) {
        this.chains = chains;
    }

    public interface Factory {

        public static Lattice lattice(Map<Account, AccountChain> chains) {
            return new Lattice(chains);
        }
    }

    public final Map<Account, AccountChain> chains() {
        return chains;
    }

    public final Lattice withChains(Map<Account, AccountChain> chains) {
        return new Lattice(chains);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return Lattice.builder().chains(chains);
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
        Lattice that = (Lattice) o;
        return java.util.Objects.equals(this.chains, that.chains);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(chains);
    }

    @Override
    public final String toString() {
        return "Lattice{chains = " + this.chains + "}";
    }

    public static final class Builder {

        public Map<Account, AccountChain> chains;

        public final Map<Account, AccountChain> chains() {
            return chains;
        }

        public final Builder chains(Map<Account, AccountChain> chains) {
            this.chains = chains;
            return this;
        }

        public final Lattice build() {
            return new Lattice(chains);
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
            Lattice.Builder that = (Lattice.Builder) o;
            return java.util.Objects.equals(this.chains, that.chains);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(chains);
        }

        @Override
        public final String toString() {
            return "Lattice.Builder{chains = " + this.chains + "}";
        }
    }
}
