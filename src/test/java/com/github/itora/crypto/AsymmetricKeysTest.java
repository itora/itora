package com.github.itora.crypto;

import java.nio.ByteBuffer;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AsymmetricKeysTest {

    @Test
    public void shouldSignAndVerify() {
    	AsymmetricKey asymmetricKey = Cryptos.generate();

    	byte[] randomBuffer = new byte[100_000];
    	Cryptos.RANDOM.nextBytes(randomBuffer);
    	Signature signature = Cryptos.sign(ByteBuffer.wrap(randomBuffer), asymmetricKey.privateKey());
    	System.out.println(signature);
    	try {
    		Cryptos.verify(signature, ByteBuffer.wrap(randomBuffer), asymmetricKey.publicKey());
    	} catch (CryptoException e) {
        	Assertions.fail("Fail", e);
    	}
    }

}
