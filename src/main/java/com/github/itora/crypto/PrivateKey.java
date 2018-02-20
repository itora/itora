package com.github.itora.crypto;

import com.github.itora.util.ByteArray;

public final class PrivateKey {

    public final ByteArray value;

    public PrivateKey(ByteArray value) {
        this.value = value;
    }

    public interface Factory {

        public static PrivateKey privateKey(ByteArray value) {
            return new PrivateKey(value);
        }
    }

    public final ByteArray value() {
        return value;
    }

    public final PrivateKey withValue(ByteArray value) {
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

        public ByteArray value;

        public final ByteArray value() {
            return value;
        }

        public final Builder value(ByteArray value) {
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
