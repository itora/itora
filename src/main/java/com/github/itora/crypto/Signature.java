package com.github.itora.crypto;

import com.github.itora.util.ByteArray;

public final class Signature {

    public final ByteArray value;

    public Signature(ByteArray value) {
        this.value = value;
    }

    public interface Factory {

        public static Signature signature(ByteArray value) {
            return new Signature(value);
        }
    }

    public final ByteArray value() {
        return value;
    }

    public final Signature withValue(ByteArray value) {
        return new Signature(value);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return Signature.builder().value(value);
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
        Signature that = (Signature) o;
        return java.util.Objects.equals(this.value, that.value);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(value);
    }

    @Override
    public final String toString() {
        return "Signature{value = " + this.value + "}";
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

        public final Signature build() {
            return new Signature(value);
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
            Signature.Builder that = (Signature.Builder) o;
            return java.util.Objects.equals(this.value, that.value);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(value);
        }

        @Override
        public final String toString() {
            return "Signature.Builder{value = " + this.value + "}";
        }
    }
}
