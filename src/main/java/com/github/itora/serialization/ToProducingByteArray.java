package com.github.itora.serialization;

import com.github.itora.util.ByteArrayProducer;

public interface ToProducingByteArray {
	void appendTo(ByteArrayProducer buffer);
}