package com.github.itora.serialization;

import com.github.itora.util.ProducingByteArray;

public interface ToProducingByteArray {
	void appendTo(ProducingByteArray buffer);
}