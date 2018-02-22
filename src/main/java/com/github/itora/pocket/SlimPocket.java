package com.github.itora.pocket;

import com.github.itora.util.ByteArray;

public final class SlimPocket<T> extends Pocket<T> {

    public final T element;

    public final ByteArray payload;

    public SlimPocket(T element, ByteArray payload) {
        this.element = element;
        this.payload = payload;
    }

    public interface Factory {

        public static <T> SlimPocket<T> slimPocket(T element, ByteArray payload) {
            return new SlimPocket<>(element, payload);
        }
    }

    @Override
    final <R> R visit(Pocket.Visitor<T, R> visitor) {
        return visitor.visitSlimPocket(this);
    }

    public final T element() {
        return element;
    }

    public final SlimPocket<T> withElement(T element) {
        return new SlimPocket<>(element, payload);
    }

    @Override
    public final ByteArray payload() {
        return payload;
    }

    @Override
    public final SlimPocket<T> withPayload(ByteArray payload) {
        return new SlimPocket<>(element, payload);
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public final Builder<T> toBuilder() {
        return SlimPocket.<T>builder().element(element).payload(payload);
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
        SlimPocket<?> that = (SlimPocket<?>) o;
        return java.util.Objects.equals(this.element, that.element) && java.util.Objects.equals(this.payload, that.payload);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(element, payload);
    }

    @Override
    public final String toString() {
        return "SlimPocket{element = " + this.element + ", payload = " + this.payload + "}";
    }

    public static final class Builder<T> {

        public T element;

        public ByteArray payload;

        public final T element() {
            return element;
        }

        public final Builder<T> element(T element) {
            this.element = element;
            return this;
        }

        public final ByteArray payload() {
            return payload;
        }

        public final Builder<T> payload(ByteArray payload) {
            this.payload = payload;
            return this;
        }

        public final SlimPocket<T> build() {
            return new SlimPocket<>(element, payload);
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
            SlimPocket.Builder<?> that = (SlimPocket.Builder<?>) o;
            return java.util.Objects.equals(this.element, that.element) && java.util.Objects.equals(this.payload, that.payload);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(element, payload);
        }

        @Override
        public final String toString() {
            return "SlimPocket.Builder{element = " + this.element + ", payload = " + this.payload + "}";
        }
    }
}
