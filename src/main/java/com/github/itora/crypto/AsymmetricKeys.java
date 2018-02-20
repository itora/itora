package com.github.itora.crypto;

import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.github.itora.util.ByteArray;

public final class AsymmetricKeys {
	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	private static final SecureRandom RANDOM = new SecureRandom();
	
	private AsymmetricKeys() {
	}

	public static AsymmetricKey generate() {
		try {
			KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA", "BC");
			gen.initialize(512, RANDOM);
			byte[] publicKey = gen.genKeyPair().getPublic().getEncoded();
			byte[] privateKey = gen.genKeyPair().getPrivate().getEncoded();
			return new AsymmetricKey(new PublicKey(new ByteArray(publicKey)), new PrivateKey(new ByteArray(privateKey)));
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}

	public static Signature sign(ByteBuffer buffer, PrivateKey privateKey) {
		ByteBuffer b = buffer.duplicate();

		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
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
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
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
