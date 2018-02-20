package com.github.itora.event;

import java.time.Instant;
import com.github.itora.tx.TxId;

public final class ReceiveEvent extends Event {

    public final TxId previous;

    public final TxId source;

    public final long pow;

    public final Instant timestamp;

    public ReceiveEvent(TxId previous, TxId source, long pow, Instant timestamp) {
        this.previous = previous;
        this.source = source;
        this.pow = pow;
        this.timestamp = timestamp;
    }

    public interface Factory {

        public static ReceiveEvent receiveEvent(TxId previous, TxId source, long pow, Instant timestamp) {
            return new ReceiveEvent(previous, source, pow, timestamp);
        }
    }

    @Override
    final <R> R visit(Event.Visitor<R> visitor) {
        return visitor.visitReceiveEvent(this);
    }

    public final TxId previous() {
        return previous;
    }

    public final ReceiveEvent withPrevious(TxId previous) {
        return new ReceiveEvent(previous, source, pow, timestamp);
    }

    public final TxId source() {
        return source;
    }

    public final ReceiveEvent withSource(TxId source) {
        return new ReceiveEvent(previous, source, pow, timestamp);
    }

    @Override
    public final long pow() {
        return pow;
    }

    @Override
    public final ReceiveEvent withPow(long pow) {
        return new ReceiveEvent(previous, source, pow, timestamp);
    }

    @Override
    public final Instant timestamp() {
        return timestamp;
    }

    @Override
    public final ReceiveEvent withTimestamp(Instant timestamp) {
        return new ReceiveEvent(previous, source, pow, timestamp);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return ReceiveEvent.builder().previous(previous).source(source).pow(pow).timestamp(timestamp);
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
        ReceiveEvent that = (ReceiveEvent) o;
        return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.source, that.source) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(previous, source, pow, timestamp);
    }

    @Override
    public final String toString() {
        return "ReceiveEvent{previous = " + this.previous + ", source = " + this.source + ", pow = " + this.pow + ", timestamp = " + this.timestamp + "}";
    }

    public static final class Builder {

        public TxId previous;

        public TxId source;

        public long pow;

        public Instant timestamp;

        public final TxId previous() {
            return previous;
        }

        public final Builder previous(TxId previous) {
            this.previous = previous;
            return this;
        }

        public final TxId source() {
            return source;
        }

        public final Builder source(TxId source) {
            this.source = source;
            return this;
        }

        public final long pow() {
            return pow;
        }

        public final Builder pow(long pow) {
            this.pow = pow;
            return this;
        }

        public final Instant timestamp() {
            return timestamp;
        }

        public final Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public final ReceiveEvent build() {
            return new ReceiveEvent(previous, source, pow, timestamp);
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
            ReceiveEvent.Builder that = (ReceiveEvent.Builder) o;
            return java.util.Objects.equals(this.previous, that.previous) && java.util.Objects.equals(this.source, that.source) && java.util.Objects.equals(this.pow, that.pow) && java.util.Objects.equals(this.timestamp, that.timestamp);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(previous, source, pow, timestamp);
        }

        @Override
        public final String toString() {
            return "ReceiveEvent.Builder{previous = " + this.previous + ", source = " + this.source + ", pow = " + this.pow + ", timestamp = " + this.timestamp + "}";
        }
    }
}
