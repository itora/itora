package com.github.itora.pocket;

import com.github.itora.util.ByteArray;
import com.github.itora.pow.Powed;

public final class PowedPocket<T> extends FatPocket<T> {

    public final Powed<T> powed;

    public final ByteArray payload;

    public final Pocket<T> inner;

    public PowedPocket(Powed<T> powed, ByteArray payload, Pocket<T> inner) {
        this.powed = powed;
        this.payload = payload;
        this.inner = inner;
    }

    public interface Factory {

        public static <T> PowedPocket<T> powedPocket(Powed<T> powed, ByteArray payload, Pocket<T> inner) {
            return new PowedPocket<>(powed, payload, inner);
        }
    }

    @Override
    final <R> R visit(FatPocket.Visitor<T, R> visitor) {
        return visitor.visitPowedPocket(this);
    }

    public final Powed<T> powed() {
        return powed;
    }

    public final PowedPocket<T> withPowed(Powed<T> powed) {
        return new PowedPocket<>(powed, payload, inner);
    }

    @Override
    public final ByteArray payload() {
        return payload;
    }

    @Override
    public final PowedPocket<T> withPayload(ByteArray payload) {
        return new PowedPocket<>(powed, payload, inner);
    }

    @Override
    public final Pocket<T> inner() {
        return inner;
    }

    @Override
    public final PowedPocket<T> withInner(Pocket<T> inner) {
        return new PowedPocket<>(powed, payload, inner);
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public final Builder<T> toBuilder() {
        return PowedPocket.<T>builder().powed(powed).payload(payload).inner(inner);
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
        PowedPocket<?> that = (PowedPocket<?>) o;
        return java.util.Objects.equals(this.powed, that.powed) && java.util.Objects.equals(this.payload, that.payload) && java.util.Objects.equals(this.inner, that.inner);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(powed, payload, inner);
    }

    @Override
    public final String toString() {
        return "PowedPocket{powed = " + this.powed + ", payload = " + this.payload + ", inner = " + this.inner + "}";
    }

    public static final class Builder<T> {

        public Powed<T> powed;

        public ByteArray payload;

        public Pocket<T> inner;

        public final Powed<T> powed() {
            return powed;
        }

        public final Builder<T> powed(Powed<T> powed) {
            this.powed = powed;
            return this;
        }

        public final ByteArray payload() {
            return payload;
        }

        public final Builder<T> payload(ByteArray payload) {
            this.payload = payload;
            return this;
        }

        public final Pocket<T> inner() {
            return inner;
        }

        public final Builder<T> inner(Pocket<T> inner) {
            this.inner = inner;
            return this;
        }

        public final PowedPocket<T> build() {
            return new PowedPocket<>(powed, payload, inner);
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
            PowedPocket.Builder<?> that = (PowedPocket.Builder<?>) o;
            return java.util.Objects.equals(this.powed, that.powed) && java.util.Objects.equals(this.payload, that.payload) && java.util.Objects.equals(this.inner, that.inner);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(powed, payload, inner);
        }

        @Override
        public final String toString() {
            return "PowedPocket.Builder{powed = " + this.powed + ", payload = " + this.payload + ", inner = " + this.inner + "}";
        }
    }
}
