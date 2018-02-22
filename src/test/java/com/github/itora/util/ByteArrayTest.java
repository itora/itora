package com.github.itora.util;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.github.itora.crypto.Cryptos;

public class ByteArrayTest {

	@Test
    public void testEqual() {
    	ByteArray randomBuffer = Cryptos.random(3, 10);
		Assertions.assertThat(randomBuffer).isEqualTo(randomBuffer.duplicate());
    }

	@Test
    public void testToString() {
    	ByteArray randomBuffer = Cryptos.random(3, 10);
		Assertions.assertThat(randomBuffer.toString()).isEqualTo(ByteArray.from(randomBuffer.toString()).toString());
    }

	@Test
    public void testHashCode() {
    	ByteArray randomBuffer = Cryptos.random(3, 10);
		Assertions.assertThat(randomBuffer.hashCode()).isEqualTo(randomBuffer.duplicate().hashCode());
    }
}
