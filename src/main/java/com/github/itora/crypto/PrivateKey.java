package com.github.itora.crypto;

import java.nio.ByteBuffer;

public final class PrivateKey {

    public final ByteBuffer value;

    public PrivateKey(ByteBuffer value) {
        this.value = value;
    }

    public interface Factory {

        public static PrivateKey privateKey(ByteBuffer value) {
            return new PrivateKey(value);
        }
    }

    public final ByteBuffer value() {
        return value;
    }

    public final PrivateKey withValue(ByteBuffer value) {
        return new PrivateKey(value);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return PrivateKey.builder().value(value);
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
        PrivateKey that = (PrivateKey) o;
        return java.util.Objects.equals(this.value, that.value);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(value);
    }

    @Override
    public final String toString() {
        return "PrivateKey{value = " + this.value + "}";
    }

    public static final class Builder {

        public ByteBuffer value;

        public final ByteBuffer value() {
            return value;
        }

        public final Builder value(ByteBuffer value) {
            this.value = value;
            return this;
        }

        public final PrivateKey build() {
            return new PrivateKey(value);
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
            PrivateKey.Builder that = (PrivateKey.Builder) o;
            return java.util.Objects.equals(this.value, that.value);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(value);
        }

        @Override
        public final String toString() {
            return "PrivateKey.Builder{value = " + this.value + "}";
        }
    }
}
