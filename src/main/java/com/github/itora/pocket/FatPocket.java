package com.github.itora.pocket;

import com.github.itora.util.ByteArray;
import com.github.itora.pow.Powed;
import com.github.itora.crypto.Signed;

public abstract class FatPocket<T> extends Pocket<T> {

    FatPocket() {
    }

    public interface Factory {

        public static <T> SignedPocket<T> signedPocket(Signed<T> signed, ByteArray payload, Pocket<T> inner) {
            return new SignedPocket<>(signed, payload, inner);
        }

        public static <T> PowedPocket<T> powedPocket(Powed<T> powed, ByteArray payload, Pocket<T> inner) {
            return new PowedPocket<>(powed, payload, inner);
        }
    }

    public static <T, R> R visit(FatPocket<T> fatPocket, FatPocket.Visitor<T, R> visitor) {
        return fatPocket.visit(visitor);
    }

    abstract <R> R visit(FatPocket.Visitor<T, R> visitor);

    @Override
    final <R> R visit(Pocket.Visitor<T, R> visitor) {
        return visit((FatPocket.Visitor<T, R>) visitor);
    }

    @Override
    public abstract ByteArray payload();

    @Override
    public abstract FatPocket<T> withPayload(ByteArray payload);

    public abstract Pocket<T> inner();

    public abstract FatPocket<T> withInner(Pocket<T> inner);

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    public interface Visitor<T, R> {

        R visitSignedPocket(SignedPocket<T> signedPocket);

        R visitPowedPocket(PowedPocket<T> powedPocket);
    }
}
