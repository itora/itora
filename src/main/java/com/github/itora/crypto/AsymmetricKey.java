package com.github.itora.crypto;

import com.github.itora.util.ByteArray;

public final class AsymmetricKey {

    public final ByteArray publicKey;

    public final ByteArray privateKey;

    public AsymmetricKey(ByteArray publicKey, ByteArray privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public interface Factory {

        public static AsymmetricKey asymmetricKey(ByteArray publicKey, ByteArray privateKey) {
            return new AsymmetricKey(publicKey, privateKey);
        }
    }

    public final ByteArray publicKey() {
        return publicKey;
    }

    public final AsymmetricKey withPublicKey(ByteArray publicKey) {
        return new AsymmetricKey(publicKey, privateKey);
    }

    public final ByteArray privateKey() {
        return privateKey;
    }

    public final AsymmetricKey withPrivateKey(ByteArray privateKey) {
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

        public ByteArray publicKey;

        public ByteArray privateKey;

        public final ByteArray publicKey() {
            return publicKey;
        }

        public final Builder publicKey(ByteArray publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public final ByteArray privateKey() {
            return privateKey;
        }

        public final Builder privateKey(ByteArray privateKey) {
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
