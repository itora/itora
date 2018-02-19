package com.github.itora.event;

public final class Block {

    public final BlockId hash;

    public final Event event;

    public Block(BlockId hash, Event event) {
        this.hash = hash;
        this.event = event;
    }

    public interface Factory {

        public static Block block(BlockId hash, Event event) {
            return new Block(hash, event);
        }
    }

    public final BlockId hash() {
        return hash;
    }

    public final Block withHash(BlockId hash) {
        return new Block(hash, event);
    }

    public final Event event() {
        return event;
    }

    public final Block withEvent(Event event) {
        return new Block(hash, event);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return Block.builder().hash(hash).event(event);
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
        Block that = (Block) o;
        return java.util.Objects.equals(this.hash, that.hash) && java.util.Objects.equals(this.event, that.event);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(hash, event);
    }

    @Override
    public final String toString() {
        return "Block{hash = " + this.hash + ", event = " + this.event + "}";
    }

    public static final class Builder {

        public BlockId hash;

        public Event event;

        public final BlockId hash() {
            return hash;
        }

        public final Builder hash(BlockId hash) {
            this.hash = hash;
            return this;
        }

        public final Event event() {
            return event;
        }

        public final Builder event(Event event) {
            this.event = event;
            return this;
        }

        public final Block build() {
            return new Block(hash, event);
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
            Block.Builder that = (Block.Builder) o;
            return java.util.Objects.equals(this.hash, that.hash) && java.util.Objects.equals(this.event, that.event);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(hash, event);
        }

        @Override
        public final String toString() {
            return "Block.Builder{hash = " + this.hash + ", event = " + this.event + "}";
        }
    }
}
