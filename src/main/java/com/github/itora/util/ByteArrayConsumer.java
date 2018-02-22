package com.github.itora.util;

import java.nio.ByteBuffer;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public final class ByteArrayConsumer {
	private final ByteArray byteArray;
	private int next = 0;
	private ByteBuffer current = null;

	public ByteArrayConsumer(ByteArray byteArray) {
		this.byteArray = byteArray;
	}

	private ByteBuffer check(int size) {
		if (current == null) {
			if (next == byteArray.bytes.length) {
				throw new ArrayIndexOutOfBoundsException();
			}
			current = ByteBuffer.wrap(byteArray.bytes[next]);
			next++;
		}
		if (current.remaining() < size) {
			throw new IllegalArgumentException("Could not consume over two internal byte arrays");
		}
		return current;
	}
	
	public byte consumeByte() {
		return check(1).get();
	}

	public int consumeInt() {
		return check(Ints.BYTES).getInt();
	}

	public long consumeLong() {
		return check(Longs.BYTES).getLong();
	}
	
	public interface Callback {
		void consume(byte[] b, int position, int length);
	}
	
	public void consume(Callback consumer, long length) {
		while (true) {
			if (length == 0L) { // Does not break if length == -1L initially
				break;
			}
			if (current == null) {
				if (next == byteArray.bytes.length) {
					throw new ArrayIndexOutOfBoundsException();
				}
				current = ByteBuffer.wrap(byteArray.bytes[next]);
				next++;
			}

			int l = (int) Math.min(length, current.remaining());
			consumer.consume(current.array(), current.position(), l);
			length -= l;
			current.position(current.position() + l);
			if (current.position() == current.limit()) {
				current = null;
			}
		}
	}
	
	public void consume(Callback consumer) {
		consume(consumer, -1L);
	}
}
