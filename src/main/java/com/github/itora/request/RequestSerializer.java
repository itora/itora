package com.github.itora.request;

import com.github.itora.util.ByteArray;

public interface RequestSerializer {
	ByteArray serialize(Request request);
	Request deserialize(ByteArray buffer);
}
