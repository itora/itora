package com.github.itora.crypto;

import java.nio.ByteBuffer;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.github.itora.util.ByteArray;

public class CryptosTest {

    @Test
    public void shouldSignAndVerify() {
    	AsymmetricKey asymmetricKey = Cryptos.generate();
    	ByteArray randomBuffer = Cryptos.random(1000);
    	Signature signature = Cryptos.sign(ByteBuffer.wrap(randomBuffer.bytes), asymmetricKey.privateKey());
		Assertions.assertThat(Cryptos.verify(signature, ByteBuffer.wrap(randomBuffer.bytes), asymmetricKey.publicKey())).isTrue();
    }

}
