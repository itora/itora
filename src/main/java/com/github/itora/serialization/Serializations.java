package com.github.itora.serialization;

import java.time.Instant;

import com.github.itora.util.ByteArray;
import com.github.itora.util.ByteArrayConsumer;
import com.github.itora.util.ByteArrayProducer;

public final class Serializations {
	private Serializations() {
	}
	
	public static ByteArray build(ToProducingByteArray... to) {
		/*
		long totalSize = 0L;
		for (ToProducingByteArray t : to) {
			totalSize += t.size();
		}
		ProducingByteArray buffer = new ProducingByteArray(totalSize);
		*/
		ByteArrayProducer buffer = new ByteArrayProducer();
		for (ToProducingByteArray t : to) {
			t.appendTo(buffer);
		}
		return buffer.finish();
	}
	
	private static final class LongTo implements ToProducingByteArray {
		private final long value;
		public LongTo(long value) {
			this.value = value;
		}
		@Override
		public void appendTo(ByteArrayProducer buffer) {
			buffer.produceLong(value);
		}
	}
	
	public static ToProducingByteArray to(long value) {
		return new LongTo(value);
	}
	public static long longFrom(ByteArrayConsumer buffer) {
		return buffer.consumeLong();
	}

	private static final class InstantTo implements ToProducingByteArray {
		private final Instant value;
		public InstantTo(Instant value) {
			this.value = value;
		}
		@Override
		public void appendTo(ByteArrayProducer buffer) {
			buffer.produceLong(value.getEpochSecond());
			buffer.produceInt(value.getNano());
		}
	}
	
	public static ToProducingByteArray to(Instant value) {
		return new InstantTo(value);
	}
	public static Instant instantFrom(ByteArrayConsumer buffer) {
		return Instant.ofEpochSecond(buffer.consumeLong(), buffer.consumeInt());
	}

	private static final class ByteArrayTo implements ToProducingByteArray {
		private final ByteArray value;
		public ByteArrayTo(ByteArray value) {
			this.value = value;
		}
		@Override
		public void appendTo(ByteArrayProducer buffer) {
			for (int i = 0; i < value.bytes.length; i++) {
				buffer.produceBytes(value.bytes[i]);
			}
		}
	}
	
	public static ToProducingByteArray to(ByteArray value) {
		return new ByteArrayTo(value);
	}
	public static ByteArray byteArrayFrom(ByteArrayConsumer buffer) {
		ByteArrayProducer p = new ByteArrayProducer();
		buffer.consume(new ByteArrayConsumer.Callback() {
			@Override
			public void consume(byte[] b, int position, int length) {
				p.produceBytes(b, position, length);
			}
		});
		return p.finish();
	}

}
