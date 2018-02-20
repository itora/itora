package com.github.itora.event;

import java.nio.ByteBuffer;

public interface SignedEventSerializer {
	ByteBuffer serialize(SignedEvent event);
	SignedEvent deserialize(ByteBuffer buffer);
}
