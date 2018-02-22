package com.github.itora.pocket;

import com.github.itora.util.ByteArray;
import com.github.itora.pow.Powed;
import com.github.itora.crypto.Signed;

public abstract class Pocket<T> {

    Pocket() {
    }

    public interface Factory {

        public static <T> SignedPocket<T> signedPocket(Signed<T> signed, ByteArray payload, Pocket<T> inner) {
            return new SignedPocket<>(signed, payload, inner);
        }

        public static <T> PowedPocket<T> powedPocket(Powed<T> powed, ByteArray payload, Pocket<T> inner) {
            return new PowedPocket<>(powed, payload, inner);
        }

        public static <T> SlimPocket<T> slimPocket(T element, ByteArray payload) {
            return new SlimPocket<>(element, payload);
        }
    }

    public static <T, R> R visit(Pocket<T> pocket, Pocket.Visitor<T, R> visitor) {
        return pocket.visit(visitor);
    }

    abstract <R> R visit(Pocket.Visitor<T, R> visitor);

    public abstract ByteArray payload();

    public abstract Pocket<T> withPayload(ByteArray payload);

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    public interface Visitor<T, R> extends FatPocket.Visitor<T, R> {

        R visitSignedPocket(SignedPocket<T> signedPocket);

        R visitPowedPocket(PowedPocket<T> powedPocket);

        R visitSlimPocket(SlimPocket<T> slimPocket);
    }
}
