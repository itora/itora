package com.github.itora.pocket;

import com.github.itora.util.ByteArray;

public final class FatPocket<T> extends Pocket<T> {

    public final T element;

    public final Pocket<T> inner;

    public final ByteArray payload;

    public FatPocket(T element, Pocket<T> inner, ByteArray payload) {
        this.element = element;
        this.inner = inner;
        this.payload = payload;
    }

    public interface Factory {

        public static <T> FatPocket<T> fatPocket(T element, Pocket<T> inner, ByteArray payload) {
            return new FatPocket<>(element, inner, payload);
        }
    }

    @Override
    final <R> R visit(Pocket.Visitor<R> visitor) {
        return visitor.visitFatPocket(this);
    }

    @Override
    public final T element() {
        return element;
    }

    @Override
    public final FatPocket<T> withElement(T element) {
        return new FatPocket<>(element, inner, payload);
    }

    public final Pocket<T> inner() {
        return inner;
    }

    public final FatPocket<T> withInner(Pocket<T> inner) {
        return new FatPocket<>(element, inner, payload);
    }

    @Override
    public final ByteArray payload() {
        return payload;
    }

    @Override
    public final FatPocket<T> withPayload(ByteArray payload) {
        return new FatPocket<>(element, inner, payload);
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public final Builder<T> toBuilder() {
        return FatPocket.<T>builder().element(element).inner(inner).payload(payload);
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
        FatPocket<?> that = (FatPocket<?>) o;
        return java.util.Objects.equals(this.element, that.element) && java.util.Objects.equals(this.inner, that.inner) && java.util.Objects.equals(this.payload, that.payload);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(element, inner, payload);
    }

    @Override
    public final String toString() {
        return "FatPocket{element = " + this.element + ", inner = " + this.inner + ", payload = " + this.payload + "}";
    }

    public static final class Builder<T> {

        public T element;

        public Pocket<T> inner;

        public ByteArray payload;

        public final T element() {
            return element;
        }

        public final Builder<T> element(T element) {
            this.element = element;
            return this;
        }

        public final Pocket<T> inner() {
            return inner;
        }

        public final Builder<T> inner(Pocket<T> inner) {
            this.inner = inner;
            return this;
        }

        public final ByteArray payload() {
            return payload;
        }

        public final Builder<T> payload(ByteArray payload) {
            this.payload = payload;
            return this;
        }

        public final FatPocket<T> build() {
            return new FatPocket<>(element, inner, payload);
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
            FatPocket.Builder<?> that = (FatPocket.Builder<?>) o;
            return java.util.Objects.equals(this.element, that.element) && java.util.Objects.equals(this.inner, that.inner) && java.util.Objects.equals(this.payload, that.payload);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(element, inner, payload);
        }

        @Override
        public final String toString() {
            return "FatPocket.Builder{element = " + this.element + ", inner = " + this.inner + ", payload = " + this.payload + "}";
        }
    }
}
