package com.github.itora.crypto;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.github.itora.util.ByteArray;
import com.github.itora.util.ConsumableByteArray;

public final class Cryptos {
    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    // Allegedly thread-safe
    public static final SecureRandom RANDOM = new SecureRandom();

    private Cryptos() {
    }

    public static ByteArray random(int size) {
    	byte[] bytes = new byte[size];
    	RANDOM.nextBytes(bytes);
    	return new ByteArray(bytes);
    }

    public static ByteArray random(int width, int size) {
    	byte[][] bytes = new byte[width][];
    	for (int i = 0; i < width; i++) {
        	bytes[i] = new byte[size];
        	RANDOM.nextBytes(bytes[i]);
		}
    	return new ByteArray(bytes);
    }

    public static ByteArray hash(ByteArray b) {
    	ConsumableByteArray buffer = new ConsumableByteArray(b);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256", "BC");
            buffer.consume(new ConsumableByteArray.Consumer() {
				@Override
				public void consume(byte[] b, int position, int length) {
		            digest.update(b, position, length);
				}
			});
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

    public static Signature sign(ByteArray b, PrivateKey privateKey) {
    	ConsumableByteArray buffer = new ConsumableByteArray(b);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            java.security.PrivateKey k = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey.value.flattened()));

            java.security.Signature s = java.security.Signature.getInstance("SHA256withRSA", "BC");
            s.initSign(k, RANDOM);

            buffer.consume(new ConsumableByteArray.Consumer() {
				@Override
				public void consume(byte[] b, int position, int length) {
		            try {
						s.update(b, position, length);
					} catch (SignatureException e) {
						throw new RuntimeException("Signature error", e);
					}
				}
			});

            return new Signature(new ByteArray(s.sign()));
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public static boolean verify(Signature signature, ByteArray b, PublicKey publicKey) {
    	ConsumableByteArray buffer = new ConsumableByteArray(b);
        try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
			java.security.PublicKey k = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey.value().flattened()));
	
			java.security.Signature s = java.security.Signature.getInstance("SHA256withRSA", "BC");
			s.initVerify(k);

            buffer.consume(new ConsumableByteArray.Consumer() {
				@Override
				public void consume(byte[] b, int position, int length) {
		            try {
						s.update(b, position, length);
					} catch (SignatureException e) {
						throw new RuntimeException("Signature error", e);
					}
				}
			});

            return s.verify(signature.value().flattened());
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }
}
