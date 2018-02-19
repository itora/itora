package com.github.itora.event;

public final class BlockId {

    public final long hash;

    public BlockId(long hash) {
        this.hash = hash;
    }

    public interface Factory {

        public static BlockId blockId(long hash) {
            return new BlockId(hash);
        }
    }

    public final long hash() {
        return hash;
    }

    public final BlockId withHash(long hash) {
        return new BlockId(hash);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return BlockId.builder().hash(hash);
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
        BlockId that = (BlockId) o;
        return java.util.Objects.equals(this.hash, that.hash);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(hash);
    }

    @Override
    public final String toString() {
        return "BlockId{hash = " + this.hash + "}";
    }

    public static final class Builder {

        public long hash;

        public final long hash() {
            return hash;
        }

        public final Builder hash(long hash) {
            this.hash = hash;
            return this;
        }

        public final BlockId build() {
            return new BlockId(hash);
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
            BlockId.Builder that = (BlockId.Builder) o;
            return java.util.Objects.equals(this.hash, that.hash);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(hash);
        }

        @Override
        public final String toString() {
            return "BlockId.Builder{hash = " + this.hash + "}";
        }
    }
}
