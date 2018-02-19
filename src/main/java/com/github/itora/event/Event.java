package com.github.itora.event;

import java.time.Instant;

import com.github.itora.account.Account;

public abstract class Event {

    Event() {
    }

    public interface Factory {

        public static OpenEvent openEvent(Account account, long pow, Instant timestamp, long signature) {
            return new OpenEvent(account, pow, timestamp, signature);
        }

        public static SendEvent sendEvent(long previous, Account from, Account to, long amount, long pow, Instant timestamp, long signature) {
            return new SendEvent(previous, from, to, amount, pow, timestamp, signature);
        }

        public static ReceiveEvent receiveEvent(long previous, Account source, Account to, long amount, long pow, Instant timestamp, long signature) {
            return new ReceiveEvent(previous, source, to, amount, pow, timestamp, signature);
        }
    }

    public static <R> R visit(Event event, Event.Visitor<R> visitor) {
        return event.visit(visitor);
    }

    abstract <R> R visit(Event.Visitor<R> visitor);

    public abstract long pow();

    public abstract Event withPow(long pow);

    public abstract Instant timestamp();

    public abstract Event withTimestamp(Instant timestamp);

    public abstract long signature();

    public abstract Event withSignature(long signature);

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    public interface Visitor<R> {

        R visitOpenEvent(OpenEvent openEvent);

        R visitSendEvent(SendEvent sendEvent);

        R visitReceiveEvent(ReceiveEvent receiveEvent);
    }
}
