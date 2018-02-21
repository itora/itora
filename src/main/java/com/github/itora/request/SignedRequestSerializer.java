package com.github.itora.request;

import java.nio.ByteBuffer;

public interface SignedRequestSerializer {
	ByteBuffer serialize(SignedRequest request);
	SignedRequest deserialize(ByteBuffer buffer);
}
