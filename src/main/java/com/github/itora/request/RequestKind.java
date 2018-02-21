package com.github.itora.request;

import java.nio.ByteBuffer;

import com.github.itora.serialization.ToByteBuffer;

class RequestKindCodes {
	static final int OPEN_CODE = 0;
	static final int SEND_CODE = 1;
	static final int RECEIVE_CODE = 2;
}

enum RequestKind implements ToByteBuffer {
	OPEN(RequestKindCodes.OPEN_CODE), SEND(RequestKindCodes.SEND_CODE), RECEIVE(RequestKindCodes.RECEIVE_CODE);

	public final int code;
	private RequestKind(int code) {
		this.code = code;
	}
	
	@Override
	public int size() {
		return 1;
	}
	
	@Override
	public void appendTo(ByteBuffer buffer) {
		buffer.put((byte) (code & 0xFF));
	}
	
	public static RequestKind requestKindFrom(ByteBuffer buffer) {
		int code = buffer.get() & 0xFF;
		switch (code) {
		case RequestKindCodes.OPEN_CODE: return OPEN;
		case RequestKindCodes.SEND_CODE: return SEND;
		case RequestKindCodes.RECEIVE_CODE: return RECEIVE;
		default: throw new SerializationException("Unknown event: " + code);
		}
	}
}
