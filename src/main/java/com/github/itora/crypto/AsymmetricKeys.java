package com.github.itora.crypto;

import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.github.itora.util.ByteArray;

public final class AsymmetricKeys {
    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    // Allegedly thread-safe
    public static final SecureRandom RANDOM = new SecureRandom();

    private AsymmetricKeys() {
    }

    public static ByteArray random(int size) {
    	byte[] bytes = new byte[size];
    	RANDOM.nextBytes(bytes);
    	return new ByteArray(bytes);
    }

    public static ByteArray hash(ByteBuffer buffer) {
        ByteBuffer b = buffer.duplicate();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256", "BC");
            digest.update(b.array(), b.position(), b.remaining());
            return new ByteArray(digest.digest());
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public static AsymmetricKey generate() {
        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA", "BC");
            gen.initialize(512, RANDOM);
            KeyPair keyPair = gen.genKeyPair();
            byte[] publicKey = keyPair.getPublic().getEncoded();
            byte[] privateKey = keyPair.getPrivate().getEncoded();
            return new AsymmetricKey(new PublicKey(new ByteArray(publicKey)), new PrivateKey(new ByteArray(privateKey)));
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public static Signature sign(ByteBuffer buffer, PrivateKey privateKey) {
        ByteBuffer b = buffer.duplicate();

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            java.security.PrivateKey k = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey.value.bytes));

            java.security.Signature s = java.security.Signature.getInstance("SHA256withRSA", "BC");
            s.initSign(k, RANDOM);

            s.update(b);

            return new Signature(new ByteArray(s.sign()));
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public static boolean verify(Signature signature, ByteBuffer buffer, PublicKey publicKey) {
		ByteBuffer b = buffer.duplicate();

        try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
			java.security.PublicKey k = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey.value().bytes));
	
			java.security.Signature s = java.security.Signature.getInstance("SHA256withRSA", "BC");
			s.initVerify(k);
			s.update(b);
	
            return s.verify(signature.value().bytes);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }
}
