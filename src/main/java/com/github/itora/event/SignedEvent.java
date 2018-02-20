package com.github.itora.event;

import com.github.itora.crypto.Signature;

public final class SignedEvent {

    public final Event event;

    public final Signature signature;

    public SignedEvent(Event event, Signature signature) {
        this.event = event;
        this.signature = signature;
    }

    public interface Factory {

        public static SignedEvent signedEvent(Event event, Signature signature) {
            return new SignedEvent(event, signature);
        }
    }

    public final Event event() {
        return event;
    }

    public final SignedEvent withEvent(Event event) {
        return new SignedEvent(event, signature);
    }

    public final Signature signature() {
        return signature;
    }

    public final SignedEvent withSignature(Signature signature) {
        return new SignedEvent(event, signature);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return SignedEvent.builder().event(event).signature(signature);
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
        SignedEvent that = (SignedEvent) o;
        return java.util.Objects.equals(this.event, that.event) && java.util.Objects.equals(this.signature, that.signature);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(event, signature);
    }

    @Override
    public final String toString() {
        return "SignedEvent{event = " + this.event + ", signature = " + this.signature + "}";
    }

    public static final class Builder {

        public Event event;

        public Signature signature;

        public final Event event() {
            return event;
        }

        public final Builder event(Event event) {
            this.event = event;
            return this;
        }

        public final Signature signature() {
            return signature;
        }

        public final Builder signature(Signature signature) {
            this.signature = signature;
            return this;
        }

        public final SignedEvent build() {
            return new SignedEvent(event, signature);
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
            SignedEvent.Builder that = (SignedEvent.Builder) o;
            return java.util.Objects.equals(this.event, that.event) && java.util.Objects.equals(this.signature, that.signature);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(event, signature);
        }

        @Override
        public final String toString() {
            return "SignedEvent.Builder{event = " + this.event + ", signature = " + this.signature + "}";
        }
    }
}
