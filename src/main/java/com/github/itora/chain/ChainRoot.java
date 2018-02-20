package com.github.itora.chain;

public final class ChainRoot extends Chain {

    public static final ChainRoot INSTANCE = new ChainRoot();

    private ChainRoot() {
    }

    public interface Factory {

        public static ChainRoot chainRoot() {
            return ChainRoot.INSTANCE;
        }
    }

    @Override
    final <R> R visit(Chain.Visitor<R> visitor) {
        return visitor.visitChainRoot(this);
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
        return true;
    }

    @Override
    public final int hashCode() {
        return -972971229;
    }

    @Override
    public final String toString() {
        return "ChainRoot{}";
    }
}
