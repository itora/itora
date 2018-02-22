package com.github.itora.serialization;

import java.time.Instant;

import com.github.itora.util.ByteArray;
import com.github.itora.util.ConsumableByteArray;
import com.github.itora.util.ProducingByteArray;

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
		ProducingByteArray buffer = new ProducingByteArray();
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
		public void appendTo(ProducingByteArray buffer) {
			buffer.produceLong(value);
		}
	}
	
	public static ToProducingByteArray to(long value) {
		return new LongTo(value);
	}
	public static long longFrom(ConsumableByteArray buffer) {
		return buffer.consumeLong();
	}

	private static final class InstantTo implements ToProducingByteArray {
		private final Instant value;
		public InstantTo(Instant value) {
			this.value = value;
		}
		@Override
		public void appendTo(ProducingByteArray buffer) {
			buffer.produceLong(value.getEpochSecond());
			buffer.produceInt(value.getNano());
		}
	}
	
	public static ToProducingByteArray to(Instant value) {
		return new InstantTo(value);
	}
	public static Instant instantFrom(ConsumableByteArray buffer) {
		return Instant.ofEpochSecond(buffer.consumeLong(), buffer.consumeInt());
	}

	private static final class ByteArrayTo implements ToProducingByteArray {
		private final ByteArray value;
		public ByteArrayTo(ByteArray value) {
			this.value = value;
		}
		@Override
		public void appendTo(ProducingByteArray buffer) {
			for (int i = 0; i < value.bytes.length; i++) {
				buffer.produceBytes(value.bytes[i]);
			}
		}
	}
	
	public static ToProducingByteArray to(ByteArray value) {
		return new ByteArrayTo(value);
	}
	public static ByteArray byteArrayFrom(ConsumableByteArray buffer) {
		ProducingByteArray p = new ProducingByteArray();
		buffer.consume(new ConsumableByteArray.Consumer() {
			@Override
			public void consume(byte[] b, int position, int length) {
				p.produceBytes(b, position, length);
			}
		});
		return p.finish();
	}

}
