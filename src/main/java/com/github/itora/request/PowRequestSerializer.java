package com.github.itora.request;

import com.github.itora.pow.Powed;
import com.github.itora.util.ByteArray;

public interface PowRequestSerializer {
	ByteArray serialize(Powed<Request> request);
	Powed<Request> deserialize(ByteArray buffer);
}
