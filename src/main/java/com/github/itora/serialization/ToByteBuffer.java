package com.github.itora.serialization;

import java.nio.ByteBuffer;

public interface ToByteBuffer {
	int size();
	void appendTo(ByteBuffer buffer);
}