package com.github.itora.util;

import java.nio.ByteBuffer;
import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.github.itora.crypto.Cryptos;
import com.github.itora.serialization.Serializations;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public class ByteArrayTest {

	private static ByteArray deepCopy(ByteArray b) {
		byte[][] bytes = new byte[b.bytes.length][];
		for (int i = 0; i < b.bytes.length; i++) {
			bytes[i] = new byte[b.bytes[i].length];
			System.arraycopy(b.bytes[i], 0, bytes[i], 0, b.bytes[i].length);
		}
		return new ByteArray(bytes);
	}
	
	@Test
    public void testEqual() {
    	ByteArray randomBuffer = Cryptos.random(3, 10);
		Assertions.assertThat(randomBuffer).isEqualTo(deepCopy(randomBuffer));
    }

	@Test
    public void testBase64Representation() {
    	ByteArray randomBuffer = Cryptos.random(3, 10);
		Assertions.assertThat(randomBuffer).isEqualTo(ByteArray.fromRepresentation(randomBuffer.representation()));
    }

	@Test
    public void testHashCode() {
    	ByteArray randomBuffer = Cryptos.random(3, 10);
		Assertions.assertThat(randomBuffer.hashCode()).isEqualTo(deepCopy(randomBuffer).hashCode());
    }

	@Test
    public void testProduceConsumeInt() {
		int value = 1000;
    	ByteArrayProducer producingByteArray = new ByteArrayProducer();
    	Serializations.to(value).appendTo(producingByteArray);
		Assertions.assertThat(value).isEqualTo(new ByteArrayConsumer(producingByteArray.finish()).consumeInt());
    }

	@Test
    public void testProduceConsumeLong() {
		long value = 1000L;
    	ByteArrayProducer producingByteArray = new ByteArrayProducer();
    	Serializations.to(value).appendTo(producingByteArray);
		Assertions.assertThat(value).isEqualTo(new ByteArrayConsumer(producingByteArray.finish()).consumeLong());
    }

	@Test
    public void testProduceConsumeInstant() {
		Instant value = Instant.EPOCH;
    	ByteArrayProducer producingByteArray = new ByteArrayProducer();
    	Serializations.to(value).appendTo(producingByteArray);
		Assertions.assertThat(value).isEqualTo(Serializations.instantFrom(new ByteArrayConsumer(producingByteArray.finish())));
    }

	@Test
    public void testProduceConsumeByteArray() {
    	ByteArray value = Cryptos.random(3, 10);
    	ByteArrayProducer producingByteArray = new ByteArrayProducer();
    	Serializations.to(value).appendTo(producingByteArray);
    	ByteArray f = producingByteArray.finish();
		Assertions.assertThat(value).isEqualTo(Serializations.byteArrayFrom(new ByteArrayConsumer(f)));
    }

	@Test
    public void testComplexProduceConsumeByteArray() {
		long longValue = 123L;
		int intValue = 456;
		ByteBuffer buf = ByteBuffer.allocate(Longs.BYTES + Ints.BYTES);
		buf.putLong(longValue).putInt(intValue);
    	
		ByteArray value = new ByteArray(buf.array());
    	ByteArrayProducer producingByteArray = new ByteArrayProducer();
    	Serializations.to(value).appendTo(producingByteArray);
    	Serializations.to(value).appendTo(producingByteArray);

    	ByteArray f = producingByteArray.finish();
    	ByteArrayConsumer c = new ByteArrayConsumer(f);
    	boolean[] called = new boolean[] { false, false, false };
    	Assertions.assertThat(c.consumeInt()).isEqualTo(1); // Number of following arrays
    	c.consume(new ByteArrayConsumer.Callback() {
			@Override
			public void consume(byte[] b, int position, int length) {
				Assertions.assertThat(ByteBuffer.wrap(b, position, length).getLong()).isEqualTo(longValue);
				called[0] = true;
			}
		}, Longs.BYTES);
    	c.consume(new ByteArrayConsumer.Callback() {
			@Override
			public void consume(byte[] b, int position, int length) {
				Assertions.assertThat(ByteBuffer.wrap(b, position, length).getInt()).isEqualTo(intValue);
				called[1] = true;
			}
		}, Ints.BYTES);
    	Assertions.assertThat(c.consumeInt()).isEqualTo(1); // Again, number of following arrays
    	c.consume(new ByteArrayConsumer.Callback() {
			@Override
			public void consume(byte[] b, int position, int length) {
				Assertions.assertThat(position).isZero();
				Assertions.assertThat(length).isEqualTo(value.bytes[0].length);
				Assertions.assertThat(b).isEqualTo(value.bytes[0]);
				called[2] = true;
			}
    	});
		Assertions.assertThat(called).isEqualTo(new boolean[] { true, true, true });
    }

	private static void checkConsume(ByteArrayConsumer consumer, ByteArray shouldBe) {
    	ByteArrayProducer p = new ByteArrayProducer();
    	consumer.consume(new ByteArrayConsumer.Callback() {
			@Override
			public void consume(byte[] b, int position, int length) {
				p.produceBytes(b, position, length);
			}
    	}, shouldBe.totalLength());
		Assertions.assertThat(p.finish()).isEqualTo(shouldBe);
	}
	@Test
    public void testCat() {
    	ByteArray a = Cryptos.random(3, 10);
    	ByteArray b = Cryptos.random(4, 11);
    	ByteArray c = Cryptos.random(5, 12);
    	ByteArray cat = ByteArray.cat(a, b, c);
    	ByteArrayConsumer consumer = new ByteArrayConsumer(cat);
    	checkConsume(consumer, a);
    	checkConsume(consumer, b);
    	checkConsume(consumer, c);
    }

}
