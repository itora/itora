package com.github.itora.request;

import java.nio.ByteBuffer;

public interface SignedPowRequestSerializer {
	ByteBuffer serialize(SignedPowRequest request);
	SignedPowRequest deserialize(ByteBuffer buffer);
}
