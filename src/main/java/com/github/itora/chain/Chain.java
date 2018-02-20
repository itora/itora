package com.github.itora.chain;

import com.github.itora.tx.Tx;

public abstract class Chain {

    Chain() {
    }

    public interface Factory {

        public static ChainRoot chainRoot() {
            return ChainRoot.INSTANCE;
        }

        public static ChainLink chainLink(Chain previous, Tx tx) {
            return new ChainLink(previous, tx);
        }
    }

    public static <R> R visit(Chain chain, Chain.Visitor<R> visitor) {
        return chain.visit(visitor);
    }

    abstract <R> R visit(Chain.Visitor<R> visitor);

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    public interface Visitor<R> {

        R visitChainRoot(ChainRoot chainRoot);

        R visitChainLink(ChainLink chainLink);
    }
}
