package com.github.itora.amount;

public final class Amount {

    public final long value;

    public Amount(long value) {
        this.value = value;
    }

    public interface Factory {

        public static Amount amount(long value) {
            return new Amount(value);
        }
    }

    public final long value() {
        return value;
    }

    public final Amount withValue(long value) {
        return new Amount(value);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return Amount.builder().value(value);
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
        Amount that = (Amount) o;
        return java.util.Objects.equals(this.value, that.value);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(value);
    }

    @Override
    public final String toString() {
        return "Amount{value = " + this.value + "}";
    }

    public static final class Builder {

        public long value;

        public final long value() {
            return value;
        }

        public final Builder value(long value) {
            this.value = value;
            return this;
        }

        public final Amount build() {
            return new Amount(value);
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
            Amount.Builder that = (Amount.Builder) o;
            return java.util.Objects.equals(this.value, that.value);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(value);
        }

        @Override
        public final String toString() {
            return "Amount.Builder{value = " + this.value + "}";
        }
    }
}
