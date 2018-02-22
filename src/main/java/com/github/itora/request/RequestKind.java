package com.github.itora.request;

import com.github.itora.serialization.ToProducingByteArray;
import com.github.itora.util.ByteArrayConsumer;
import com.github.itora.util.ByteArrayProducer;

class RequestKindCodes {
	static final byte OPEN_CODE = 0;
	static final byte SEND_CODE = 1;
	static final byte RECEIVE_CODE = 2;
}

enum RequestKind implements ToProducingByteArray {
	OPEN(RequestKindCodes.OPEN_CODE), SEND(RequestKindCodes.SEND_CODE), RECEIVE(RequestKindCodes.RECEIVE_CODE);

	public final byte code;
	private RequestKind(byte code) {
		this.code = code;
	}
	
	@Override
	public void appendTo(ByteArrayProducer buffer) {
		buffer.produceByte(code);
	}
	
	public static RequestKind requestKindFrom(ByteArrayConsumer buffer) {
		byte code = buffer.consumeByte();
		switch (code) {
		case RequestKindCodes.OPEN_CODE: return OPEN;
		case RequestKindCodes.SEND_CODE: return SEND;
		case RequestKindCodes.RECEIVE_CODE: return RECEIVE;
		default: throw new SerializationException("Unknown event: " + code);
		}
	}
}
