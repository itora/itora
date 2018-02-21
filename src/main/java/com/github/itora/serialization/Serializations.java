package com.github.itora.serialization;

import java.nio.ByteBuffer;
import java.time.Instant;

import com.github.itora.util.ByteArray;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public final class Serializations {
	private Serializations() {
	}
	
	public static ByteBuffer build(ToByteBuffer... to) {
		int totalSize = 0;
		for (ToByteBuffer t : to) {
			totalSize += t.size();
		}
		ByteBuffer buffer = ByteBuffer.allocate(totalSize);
		for (ToByteBuffer t : to) {
			t.appendTo(buffer);
		}
		buffer.flip();
		return buffer;
	}
	
	private static final class LongToByteBuffer implements ToByteBuffer {
		private final long value;
		public LongToByteBuffer(long value) {
			this.value = value;
		}
		@Override
		public int size() {
			return Longs.BYTES;
		}
		@Override
		public void appendTo(ByteBuffer buffer) {
			buffer.putLong(value);
		}
	}
	
	public static ToByteBuffer to(long value) {
		return new LongToByteBuffer(value);
	}
	public static long longFrom(ByteBuffer buffer) {
		return buffer.getLong();
	}

	private static final class InstantToByteBuffer implements ToByteBuffer {
		private final Instant value;
		public InstantToByteBuffer(Instant value) {
			this.value = value;
		}
		@Override
		public int size() {
			return Longs.BYTES + Ints.BYTES;
		}
		@Override
		public void appendTo(ByteBuffer buffer) {
			buffer.putLong(value.getEpochSecond());
			buffer.putInt(value.getNano());
		}
	}
	
	public static ToByteBuffer to(Instant value) {
		return new InstantToByteBuffer(value);
	}
	public static Instant instantFrom(ByteBuffer buffer) {
		return Instant.ofEpochSecond(buffer.getLong(), buffer.getInt());
	}

	private static final class ByteArrayToByteBuffer implements ToByteBuffer {
		private final ByteArray value;
		public ByteArrayToByteBuffer(ByteArray value) {
			this.value = value;
		}
		@Override
		public int size() {
			return Ints.BYTES + value.bytes.length;
		}
		@Override
		public void appendTo(ByteBuffer buffer) {
			buffer.putInt(value.bytes.length); //TODO Optimize size
			buffer.put(value.bytes);
		}
	}
	
	public static ToByteBuffer to(ByteArray value) {
		return new ByteArrayToByteBuffer(value);
	}
	public static ByteArray byteArrayFrom(ByteBuffer buffer) {
		int size = buffer.getInt();
		byte[] bytes = new byte[size]; //TODO Verif too big sizes
		buffer.get(bytes);
		return new ByteArray(bytes);
	}

}
