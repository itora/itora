package com.github.itora.pow;

public final class Powed<T> {

    public final T element;

    public final Pow pow;

    public Powed(T element, Pow pow) {
        this.element = element;
        this.pow = pow;
    }

    public interface Factory {

        public static <T> Powed<T> powed(T element, Pow pow) {
            return new Powed<>(element, pow);
        }
    }

    public final T element() {
        return element;
    }

    public final Powed<T> withElement(T element) {
        return new Powed<>(element, pow);
    }

    public final Pow pow() {
        return pow;
    }

    public final Powed<T> withPow(Pow pow) {
        return new Powed<>(element, pow);
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public final Builder<T> toBuilder() {
        return Powed.<T>builder().element(element).pow(pow);
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
        Powed<?> that = (Powed<?>) o;
        return java.util.Objects.equals(this.element, that.element) && java.util.Objects.equals(this.pow, that.pow);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(element, pow);
    }

    @Override
    public final String toString() {
        return "Powed{element = " + this.element + ", pow = " + this.pow + "}";
    }

    public static final class Builder<T> {

        public T element;

        public Pow pow;

        public final T element() {
            return element;
        }

        public final Builder<T> element(T element) {
            this.element = element;
            return this;
        }

        public final Pow pow() {
            return pow;
        }

        public final Builder<T> pow(Pow pow) {
            this.pow = pow;
            return this;
        }

        public final Powed<T> build() {
            return new Powed<>(element, pow);
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
            Powed.Builder<?> that = (Powed.Builder<?>) o;
            return java.util.Objects.equals(this.element, that.element) && java.util.Objects.equals(this.pow, that.pow);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(element, pow);
        }

        @Override
        public final String toString() {
            return "Powed.Builder{element = " + this.element + ", pow = " + this.pow + "}";
        }
    }
}
