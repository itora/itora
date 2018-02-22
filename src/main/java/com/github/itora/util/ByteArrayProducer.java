package com.github.itora.util;

import java.nio.ByteBuffer;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public final class ByteArrayProducer {
	private final List<ByteBuffer> bytes = Lists.newArrayList();
	private ByteBuffer current = null;

	public ByteArrayProducer() {
	}
	
	public ByteArray finish() {
		byte[][] bb = new byte[bytes.size()][];
		int i = 0;
		for (ByteBuffer b : bytes) {
			b.flip();
			if ((b.position() == 0) && (b.limit() == b.array().length)) {
				bb[i] = b.array();
			} else {
				bb[i] = new byte[b.remaining()];
				b.get(bb[i]);
			}
		}
		return new ByteArray(bb);
	}

	private ByteBuffer check(int size) {
		if (current != null) {
			if (current.remaining() < size) {
				current = null;
			}
		}
		if (current == null) {
			current = ByteBuffer.allocate(100);
			bytes.add(current);
		}
		return current;
	}
	
	public ByteArrayProducer produceByte(byte value) {
		check(1).put(value);
		return this;
	}
	
	public ByteArrayProducer produceInt(int value) {
		check(Ints.BYTES).putInt(value);
		return this;
	}
	
	public ByteArrayProducer produceLong(long value) {	
		check(Longs.BYTES).putLong(value);
		return this;
	}
	
	public ByteArrayProducer produceBytes(byte[] value, int position, int length) {
		if ((position == 0) && (length == value.length)) {
			current = null;
			bytes.add(ByteBuffer.wrap(value));
		} else {
			check(length).put(value, position, length);
		}
		return this;
	}
	public ByteArrayProducer produceBytes(byte[] value) {
		produceBytes(value, 0, value.length);
		return this;
	}
}
