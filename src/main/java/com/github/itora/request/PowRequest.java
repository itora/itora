package com.github.itora.request;

import com.github.itora.util.ByteArray;

public final class PowRequest {

    public final Request request;

    public final ByteArray pow;

    public PowRequest(Request request, ByteArray pow) {
        this.request = request;
        this.pow = pow;
    }

    public interface Factory {

        public static PowRequest powRequest(Request request, ByteArray pow) {
            return new PowRequest(request, pow);
        }
    }

    public final Request request() {
        return request;
    }

    public final PowRequest withRequest(Request request) {
        return new PowRequest(request, pow);
    }

    public final ByteArray pow() {
        return pow;
    }

    public final PowRequest withPow(ByteArray pow) {
        return new PowRequest(request, pow);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return PowRequest.builder().request(request).pow(pow);
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
        PowRequest that = (PowRequest) o;
        return java.util.Objects.equals(this.request, that.request) && java.util.Objects.equals(this.pow, that.pow);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(request, pow);
    }

    @Override
    public final String toString() {
        return "PowRequest{request = " + this.request + ", pow = " + this.pow + "}";
    }

    public static final class Builder {

        public Request request;

        public ByteArray pow;

        public final Request request() {
            return request;
        }

        public final Builder request(Request request) {
            this.request = request;
            return this;
        }

        public final ByteArray pow() {
            return pow;
        }

        public final Builder pow(ByteArray pow) {
            this.pow = pow;
            return this;
        }

        public final PowRequest build() {
            return new PowRequest(request, pow);
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
            PowRequest.Builder that = (PowRequest.Builder) o;
            return java.util.Objects.equals(this.request, that.request) && java.util.Objects.equals(this.pow, that.pow);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(request, pow);
        }

        @Override
        public final String toString() {
            return "PowRequest.Builder{request = " + this.request + ", pow = " + this.pow + "}";
        }
    }
}
