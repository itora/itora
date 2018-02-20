package com.github.itora.crypto;

import java.nio.ByteBuffer;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AsymmetricKeysTest {

    @Test
    public void shouldSignAndVerify() {
    	AsymmetricKey asymmetricKey = AsymmetricKeys.generate();

    	byte[] randomBuffer = new byte[100_000];
    	AsymmetricKeys.RANDOM.nextBytes(randomBuffer);
    	Signature signature = AsymmetricKeys.sign(ByteBuffer.wrap(randomBuffer), asymmetricKey.privateKey());
    	System.out.println(signature);
    	Assertions.assertThat(AsymmetricKeys.verify(signature, ByteBuffer.wrap(randomBuffer), asymmetricKey.publicKey())).isTrue();
    }

}
