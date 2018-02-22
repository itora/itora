package com.github.itora.pocket;

import com.github.itora.util.ByteArray;
import com.github.itora.crypto.Signed;

public final class SignedPocket<T> extends FatPocket<T> {

    public final Signed<T> signed;

    public final ByteArray payload;

    public final Pocket<T> inner;

    public SignedPocket(Signed<T> signed, ByteArray payload, Pocket<T> inner) {
        this.signed = signed;
        this.payload = payload;
        this.inner = inner;
    }

    public interface Factory {

        public static <T> SignedPocket<T> signedPocket(Signed<T> signed, ByteArray payload, Pocket<T> inner) {
            return new SignedPocket<>(signed, payload, inner);
        }
    }

    @Override
    final <R> R visit(FatPocket.Visitor<T, R> visitor) {
        return visitor.visitSignedPocket(this);
    }

    public final Signed<T> signed() {
        return signed;
    }

    public final SignedPocket<T> withSigned(Signed<T> signed) {
        return new SignedPocket<>(signed, payload, inner);
    }

    @Override
    public final ByteArray payload() {
        return payload;
    }

    @Override
    public final SignedPocket<T> withPayload(ByteArray payload) {
        return new SignedPocket<>(signed, payload, inner);
    }

    @Override
    public final Pocket<T> inner() {
        return inner;
    }

    @Override
    public final SignedPocket<T> withInner(Pocket<T> inner) {
        return new SignedPocket<>(signed, payload, inner);
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public final Builder<T> toBuilder() {
        return SignedPocket.<T>builder().signed(signed).payload(payload).inner(inner);
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
        SignedPocket<?> that = (SignedPocket<?>) o;
        return java.util.Objects.equals(this.signed, that.signed) && java.util.Objects.equals(this.payload, that.payload) && java.util.Objects.equals(this.inner, that.inner);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(signed, payload, inner);
    }

    @Override
    public final String toString() {
        return "SignedPocket{signed = " + this.signed + ", payload = " + this.payload + ", inner = " + this.inner + "}";
    }

    public static final class Builder<T> {

        public Signed<T> signed;

        public ByteArray payload;

        public Pocket<T> inner;

        public final Signed<T> signed() {
            return signed;
        }

        public final Builder<T> signed(Signed<T> signed) {
            this.signed = signed;
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

        public final SignedPocket<T> build() {
            return new SignedPocket<>(signed, payload, inner);
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
            SignedPocket.Builder<?> that = (SignedPocket.Builder<?>) o;
            return java.util.Objects.equals(this.signed, that.signed) && java.util.Objects.equals(this.payload, that.payload) && java.util.Objects.equals(this.inner, that.inner);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(signed, payload, inner);
        }

        @Override
        public final String toString() {
            return "SignedPocket.Builder{signed = " + this.signed + ", payload = " + this.payload + ", inner = " + this.inner + "}";
        }
    }
}
