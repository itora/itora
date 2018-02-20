package com.github.itora.tx;

import com.github.itora.util.ByteArray;

public final class TxId {

    public final ByteArray id;

    public TxId(ByteArray id) {
        this.id = id;
    }

    public interface Factory {

        public static TxId txId(ByteArray id) {
            return new TxId(id);
        }
    }

    public final ByteArray id() {
        return id;
    }

    public final TxId withId(ByteArray id) {
        return new TxId(id);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return TxId.builder().id(id);
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
        TxId that = (TxId) o;
        return java.util.Objects.equals(this.id, that.id);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public final String toString() {
        return "TxId{id = " + this.id + "}";
    }

    public static final class Builder {

        public ByteArray id;

        public final ByteArray id() {
            return id;
        }

        public final Builder id(ByteArray id) {
            this.id = id;
            return this;
        }

        public final TxId build() {
            return new TxId(id);
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
            TxId.Builder that = (TxId.Builder) o;
            return java.util.Objects.equals(this.id, that.id);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(id);
        }

        @Override
        public final String toString() {
            return "TxId.Builder{id = " + this.id + "}";
        }
    }
}
