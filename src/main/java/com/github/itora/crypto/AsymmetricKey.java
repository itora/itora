package com.github.itora.crypto;

public final class AsymmetricKey {

    public final PublicKey publicKey;

    public final PrivateKey privateKey;

    public AsymmetricKey(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public interface Factory {

        public static AsymmetricKey asymmetricKey(PublicKey publicKey, PrivateKey privateKey) {
            return new AsymmetricKey(publicKey, privateKey);
        }
    }

    public final PublicKey publicKey() {
        return publicKey;
    }

    public final AsymmetricKey withPublicKey(PublicKey publicKey) {
        return new AsymmetricKey(publicKey, privateKey);
    }

    public final PrivateKey privateKey() {
        return privateKey;
    }

    public final AsymmetricKey withPrivateKey(PrivateKey privateKey) {
        return new AsymmetricKey(publicKey, privateKey);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final Builder toBuilder() {
        return AsymmetricKey.builder().publicKey(publicKey).privateKey(privateKey);
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
        AsymmetricKey that = (AsymmetricKey) o;
        return java.util.Objects.equals(this.publicKey, that.publicKey) && java.util.Objects.equals(this.privateKey, that.privateKey);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(publicKey, privateKey);
    }

    @Override
    public final String toString() {
        return "AsymmetricKey{publicKey = " + this.publicKey + ", privateKey = " + this.privateKey + "}";
    }

    public static final class Builder {

        public PublicKey publicKey;

        public PrivateKey privateKey;

        public final PublicKey publicKey() {
            return publicKey;
        }

        public final Builder publicKey(PublicKey publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public final PrivateKey privateKey() {
            return privateKey;
        }

        public final Builder privateKey(PrivateKey privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public final AsymmetricKey build() {
            return new AsymmetricKey(publicKey, privateKey);
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
            AsymmetricKey.Builder that = (AsymmetricKey.Builder) o;
            return java.util.Objects.equals(this.publicKey, that.publicKey) && java.util.Objects.equals(this.privateKey, that.privateKey);
        }

        @Override
        public final int hashCode() {
            return java.util.Objects.hash(publicKey, privateKey);
        }

        @Override
        public final String toString() {
            return "AsymmetricKey.Builder{publicKey = " + this.publicKey + ", privateKey = " + this.privateKey + "}";
        }
    }
}
