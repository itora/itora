package com.github.itora.util;

import java.nio.ByteBuffer;
import java.util.List;

import org.assertj.core.util.Lists;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public final class ProducingByteArray {
	private final List<ByteBuffer> bytes = Lists.newArrayList();
	private ByteBuffer current = null;

	public ProducingByteArray() {
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
	
	public void produceByte(byte value) {
		check(1).put(value);
	}
	
	public void produceInt(int value) {
		check(Ints.BYTES).putInt(value);
	}
	
	public void produceLong(long value) {	
		check(Longs.BYTES).putLong(value);
	}
	
	public void produceBytes(byte[] value, int position, int length) {
		if ((position == 0) && (length == value.length)) {
			current = null;
			bytes.add(ByteBuffer.wrap(value));
		} else {
			check(length).put(value, position, length);
		}
	}
	public void produceBytes(byte[] value) {
		produceBytes(value, 0, value.length);
	}
}
