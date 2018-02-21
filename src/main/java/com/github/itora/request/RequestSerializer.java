package com.github.itora.request;

import java.nio.ByteBuffer;

public interface RequestSerializer {
	ByteBuffer serialize(Request request);
	Request deserialize(ByteBuffer buffer);
}
