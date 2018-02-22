package com.github.itora.serialization;

import java.time.Instant;

import com.github.itora.util.ByteArray;
import com.github.itora.util.ByteArrayConsumer;
import com.github.itora.util.ByteArrayProducer;

public final class Serializations {
	private Serializations() {
	}
	
	public static ByteArray build(ToProducingByteArray... to) {
		ByteArrayProducer buffer = new ByteArrayProducer();
		for (ToProducingByteArray t : to) {
			t.appendTo(buffer);
		}
		return buffer.finish();
	}
	
	private static final class IntTo implements ToProducingByteArray {
		private final int value;
		public IntTo(int value) {
			this.value = value;
		}
		@Override
		public void appendTo(ByteArrayProducer buffer) {
			buffer.produceInt(value);
		}
	}
	
	public static ToProducingByteArray to(int value) {
		return new IntTo(value);
	}
	public static int intFrom(ByteArrayConsumer buffer) {
		return buffer.consumeInt();
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
			buffer.produceInt(value.bytes.length);
			for (byte[] b : value.bytes) {
				buffer.produceBytes(b);
			}
		}
	}
	
	public static ToProducingByteArray to(ByteArray value) {
		return new ByteArrayTo(value);
	}
	public static ByteArray byteArrayFrom(ByteArrayConsumer buffer) {
		byte[][] bytes = new byte[buffer.consumeInt()][];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = buffer.consumeBytes();
		}
		return new ByteArray(bytes);
	}

}
