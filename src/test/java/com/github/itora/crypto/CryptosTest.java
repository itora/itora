package com.github.itora.crypto;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.github.itora.util.ByteArray;

public class CryptosTest {

    @Test
    public void shouldSignAndVerify() {
    	AsymmetricKey asymmetricKey = Cryptos.generate();
    	ByteArray randomBuffer = Cryptos.random(1000);
    	Signature signature = Cryptos.sign(randomBuffer, asymmetricKey.privateKey());
		Assertions.assertThat(Cryptos.verify(signature, randomBuffer, asymmetricKey.publicKey())).isTrue();
    }

}
