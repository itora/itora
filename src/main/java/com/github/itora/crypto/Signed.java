package com.github.itora.crypto;

public final class Signed<T> {

    public final T element;

    public final Signature signature;

    public Signed(T element, Signature signature) {
        this.element = element;
        this.signature = signature;
    }

    public interface Factory {

        public static <T> Signed<T> signed(T element, Signature signature) {
            return new Signed<>(element, signature);
        }
    }

    public final T element() {
        return element;
    }

    public final Signed<T> withElement(T element) {
        return new Signed<>(element, signature);
    }

    public final Signature signature() {
        return signature;
    }

    public final Signed<T> withSignature(Signature signature) {
        return new Signed<>(element, signature);
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public final Builder<T> toBuilder() {
        return Signed.<T>builder().element(element).signature(signature);
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
        Signed<?> that = (Signed<?>) o;
        return java.util.Objects.equals(this.element, that.element) && java.util.Objects.equals(this.signature, that.signature);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(element, signature);
    }

    @Override
    public final String toString() {
        return "Signed{element = " + this.element + ", signature = " + this.signature + "}";
    }

    public static final class Builder<T> {

        public T element;

        public Signature signature;

        public final T element() {
            return element;
        }

        public final Builder<T> element(T element) {
            this.element = element;
            return this;
        }

        public final Signature signature() {
            return signature;
        }

        public final Builder<T> signature(Signature signature) {
            this.signature = signature;
            return this;
        }

        public final Signed<T> build() {
            return new Signed<>(element, signature);
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
            Signed.Builder<?> that = (Signed.Builder<?>) o;
            return java.util.Objects.equals(this.element, that.element) && java.util.Objects.equals(this.signature, that.signature);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(element, signature);
        }

        @Override
        public final String toString() {
            return "Signed.Builder{element = " + this.element + ", signature = " + this.signature + "}";
        }
    }
}
