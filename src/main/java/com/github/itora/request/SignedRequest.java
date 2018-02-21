package com.github.itora.request;

import com.github.itora.crypto.Signature;

public final class SignedRequest {

    public final Request request;

    public final Signature signature;

    public SignedRequest(Request request, Signature signature) {
        this.request = request;
        this.signature = signature;
    }

    public interface Factory {

        public static SignedRequest signedRequest(Request request, Signature signature) {
            return new SignedRequest(request, signature);
        }
    }

    public final Request request() {
        return request;
    }

    public final SignedRequest withRequest(Request request) {
        return new SignedRequest(request, signature);
    }

    public final Signature signature() {
        return signature;
    }

    public final SignedRequest withSignature(Signature signature) {
        return new SignedRequest(request, signature);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return SignedRequest.builder().request(request).signature(signature);
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
        SignedRequest that = (SignedRequest) o;
        return java.util.Objects.equals(this.request, that.request) && java.util.Objects.equals(this.signature, that.signature);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(request, signature);
    }

    @Override
    public final String toString() {
        return "SignedRequest{request = " + this.request + ", signature = " + this.signature + "}";
    }

    public static final class Builder {

        public Request request;

        public Signature signature;

        public final Request request() {
            return request;
        }

        public final Builder request(Request request) {
            this.request = request;
            return this;
        }

        public final Signature signature() {
            return signature;
        }

        public final Builder signature(Signature signature) {
            this.signature = signature;
            return this;
        }

        public final SignedRequest build() {
            return new SignedRequest(request, signature);
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
            SignedRequest.Builder that = (SignedRequest.Builder) o;
            return java.util.Objects.equals(this.request, that.request) && java.util.Objects.equals(this.signature, that.signature);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(request, signature);
        }

        @Override
        public final String toString() {
            return "SignedRequest.Builder{request = " + this.request + ", signature = " + this.signature + "}";
        }
    }
}
