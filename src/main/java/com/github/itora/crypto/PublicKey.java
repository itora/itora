package com.github.itora.crypto;

import com.github.itora.util.ByteArray;

public final class PublicKey {

    public final ByteArray value;

    public PublicKey(ByteArray value) {
        this.value = value;
    }

    public interface Factory {

        public static PublicKey publicKey(ByteArray value) {
            return new PublicKey(value);
        }
    }

    public final ByteArray value() {
        return value;
    }

    public final PublicKey withValue(ByteArray value) {
        return new PublicKey(value);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return PublicKey.builder().value(value);
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
        PublicKey that = (PublicKey) o;
        return java.util.Objects.equals(this.value, that.value);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(value);
    }

    @Override
    public final String toString() {
        return "PublicKey{value = " + this.value + "}";
    }

    public static final class Builder {

        public ByteArray value;

        public final ByteArray value() {
            return value;
        }

        public final Builder value(ByteArray value) {
            this.value = value;
            return this;
        }

        public final PublicKey build() {
            return new PublicKey(value);
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
            PublicKey.Builder that = (PublicKey.Builder) o;
            return java.util.Objects.equals(this.value, that.value);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(value);
        }

        @Override
        public final String toString() {
            return "PublicKey.Builder{value = " + this.value + "}";
        }
    }
}
