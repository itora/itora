package com.github.itora.request;

import java.nio.ByteBuffer;

import com.github.itora.crypto.Signature;
import com.github.itora.util.ByteArray;
import com.google.common.primitives.Ints;

public final class RegularSignedRequestSerializer implements SignedRequestSerializer {
	public RegularSignedRequestSerializer() {
	}
	
	private static interface ToByteBuffer {
		int size();
		void appendTo(ByteBuffer buffer);
	}

	private static final class ByteArrayToByteBuffer implements ToByteBuffer {
		private final ByteArray value;
		public ByteArrayToByteBuffer(ByteArray value) {
			this.value = value;
		}
		@Override
		public int size() {
			return Ints.BYTES + value.bytes.length;
		}
		@Override
		public void appendTo(ByteBuffer buffer) {
			buffer.putInt(value.bytes.length); //TODO Optimize size
			buffer.put(value.bytes);
		}
	}
	
	private static final class ByteBufferToByteBuffer implements ToByteBuffer {
		private final ByteBuffer value;
		public ByteBufferToByteBuffer(ByteBuffer value) {
			this.value = value.duplicate();
		}
		@Override
		public int size() {
			return Ints.BYTES + value.remaining();
		}
		@Override
		public void appendTo(ByteBuffer buffer) {
			buffer.putInt(value.remaining()); //TODO Optimize size
			buffer.put(value.duplicate());
		}
	}
	
	private static ByteBuffer build(ToByteBuffer... to) {
		int totalSize = 0;
		for (ToByteBuffer t : to) {
			totalSize += t.size();
		}
		ByteBuffer buffer = ByteBuffer.allocate(totalSize);
		for (ToByteBuffer t : to) {
			t.appendTo(buffer);
		}
		buffer.flip();
		return buffer;
	}
	
	@Override
	public ByteBuffer serialize(SignedRequest request) {
		return build(
			new ByteBufferToByteBuffer(new RegularRequestSerializer().serialize(request.request())),
			new ByteArrayToByteBuffer(request.signature().value())
		);
	}

	private static ByteArray byteArrayFrom(ByteBuffer buffer) {
		int size = buffer.getInt();
		byte[] bytes = new byte[size]; //TODO Verif too big sizes
		buffer.get(bytes);
		return new ByteArray(bytes);
	}
	
	@Override
	public SignedRequest deserialize(ByteBuffer buffer) {
		return SignedRequest.Factory.signedRequest(
			new RegularRequestSerializer().deserialize(buffer),
			Signature.Factory.signature(byteArrayFrom(buffer))
		);
	}
}
