package com.github.itora.request;

import com.github.itora.crypto.Signature;

public final class SignedPowRequest {

    public final PowRequest powRequest;

    public final Signature signature;

    public SignedPowRequest(PowRequest powRequest, Signature signature) {
        this.powRequest = powRequest;
        this.signature = signature;
    }

    public interface Factory {

        public static SignedPowRequest signedPowRequest(PowRequest powRequest, Signature signature) {
            return new SignedPowRequest(powRequest, signature);
        }
    }

    public final PowRequest powRequest() {
        return powRequest;
    }

    public final SignedPowRequest withPowRequest(PowRequest powRequest) {
        return new SignedPowRequest(powRequest, signature);
    }

    public final Signature signature() {
        return signature;
    }

    public final SignedPowRequest withSignature(Signature signature) {
        return new SignedPowRequest(powRequest, signature);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return SignedPowRequest.builder().powRequest(powRequest).signature(signature);
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
        SignedPowRequest that = (SignedPowRequest) o;
        return java.util.Objects.equals(this.powRequest, that.powRequest) && java.util.Objects.equals(this.signature, that.signature);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(powRequest, signature);
    }

    @Override
    public final String toString() {
        return "SignedPowRequest{powRequest = " + this.powRequest + ", signature = " + this.signature + "}";
    }

    public static final class Builder {

        public PowRequest powRequest;

        public Signature signature;

        public final PowRequest powRequest() {
            return powRequest;
        }

        public final Builder powRequest(PowRequest powRequest) {
            this.powRequest = powRequest;
            return this;
        }

        public final Signature signature() {
            return signature;
        }

        public final Builder signature(Signature signature) {
            this.signature = signature;
            return this;
        }

        public final SignedPowRequest build() {
            return new SignedPowRequest(powRequest, signature);
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
            SignedPowRequest.Builder that = (SignedPowRequest.Builder) o;
            return java.util.Objects.equals(this.powRequest, that.powRequest) && java.util.Objects.equals(this.signature, that.signature);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(powRequest, signature);
        }

        @Override
        public final String toString() {
            return "SignedPowRequest.Builder{powRequest = " + this.powRequest + ", signature = " + this.signature + "}";
        }
    }
}
