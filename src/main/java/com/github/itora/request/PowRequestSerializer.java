package com.github.itora.request;

import java.nio.ByteBuffer;

public interface PowRequestSerializer {
	ByteBuffer serialize(PowRequest request);
	PowRequest deserialize(ByteBuffer buffer);
}
