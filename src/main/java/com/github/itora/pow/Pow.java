package com.github.itora.pow;

import com.github.itora.util.ByteArray;

public final class Pow {

    public final ByteArray value;

    public Pow(ByteArray value) {
        this.value = value;
    }

    public interface Factory {

        public static Pow pow(ByteArray value) {
            return new Pow(value);
        }
    }

    public final ByteArray value() {
        return value;
    }

    public final Pow withValue(ByteArray value) {
        return new Pow(value);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return Pow.builder().value(value);
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
        Pow that = (Pow) o;
        return java.util.Objects.equals(this.value, that.value);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(value);
    }

    @Override
    public final String toString() {
        return "Pow{value = " + this.value + "}";
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

        public final Pow build() {
            return new Pow(value);
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
            Pow.Builder that = (Pow.Builder) o;
            return java.util.Objects.equals(this.value, that.value);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(value);
        }

        @Override
        public final String toString() {
            return "Pow.Builder{value = " + this.value + "}";
        }
    }
}
