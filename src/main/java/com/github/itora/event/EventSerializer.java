package com.github.itora.event;

import java.nio.ByteBuffer;

public interface EventSerializer {
	ByteBuffer serialize(Event event);
	Event deserialize(ByteBuffer buffer);
}
