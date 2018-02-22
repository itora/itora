package com.github.itora.pocket;

import com.github.itora.util.ByteArray;

public abstract class Pocket<T> {

    Pocket() {
    }

    public interface Factory {

        public static <T> FatPocket<T> fatPocket(T element, Pocket<T> inner, ByteArray payload) {
            return new FatPocket<>(element, inner, payload);
        }

        public static <T> SlimPocket<T> slimPocket(T element, ByteArray payload) {
            return new SlimPocket<>(element, payload);
        }
    }

    public static <R> R visit(Pocket<T> pocket, Pocket.Visitor<R> visitor) {
        return pocket.visit(visitor);
    }

    abstract <R> R visit(Pocket.Visitor<R> visitor);

    public abstract T element();

    public abstract Pocket<T> withElement(T element);

    public abstract ByteArray payload();

    public abstract Pocket<T> withPayload(ByteArray payload);

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    public interface Visitor<R> {

        R visitFatPocket(FatPocket<T> fatPocket);

        R visitSlimPocket(SlimPocket<T> slimPocket);
    }
}
