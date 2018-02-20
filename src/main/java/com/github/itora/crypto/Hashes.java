package com.github.itora.crypto;

import java.nio.ByteBuffer;

import com.github.itora.util.ByteArray;

public final class Hashes {
	private Hashes() {
	}
	
	public static ByteArray hash(ByteBuffer buffer) {
		ByteBuffer bb = buffer.duplicate();
		byte[] b = new byte[bb.remaining()];
		bb.get(b);
		return new ByteArray(b);
	}
}
