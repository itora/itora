package com.github.itora.event.internal;

import java.nio.ByteBuffer;
import java.time.Instant;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import com.github.itora.crypto.PublicKey;
import com.github.itora.event.Event;
import com.github.itora.event.EventSerializer;
import com.github.itora.event.OpenEvent;
import com.github.itora.event.ReceiveEvent;
import com.github.itora.event.SendEvent;
import com.github.itora.event.SerializationException;
import com.github.itora.tx.TxId;
import com.github.itora.util.ByteArray;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public final class EventSerializerImpl implements EventSerializer {
	public EventSerializerImpl() {
	}
	
	private static interface ToByteBuffer {
		int size();
		void appendTo(ByteBuffer buffer);
	}

	private static final int OPEN_CODE = 0;
	private static final int SEND_CODE = 1;
	private static final int RECEIVE_CODE = 2;
	
	private static enum EventKind implements ToByteBuffer {
		OPEN(OPEN_CODE), SEND(SEND_CODE), RECEIVE(RECEIVE_CODE);

		public final int code;
		private EventKind(int code) {
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
		
		public static EventKind parse(ByteBuffer buffer) {
			int code = buffer.get() & 0xFF;
			switch (code) {
			case OPEN_CODE: return OPEN;
			case SEND_CODE: return SEND;
			case RECEIVE_CODE: return RECEIVE;
			default: throw new SerializationException("Unknown event: " + code);
			}
		}
	};
	
	private static final class LongToByteBuffer implements ToByteBuffer {
		private final long value;
		public LongToByteBuffer(long value) {
			this.value = value;
		}
		@Override
		public int size() {
			return Longs.BYTES;
		}
		@Override
		public void appendTo(ByteBuffer buffer) {
			buffer.putLong(value);
		}
	}
	private static final class PublicKeyToByteBuffer implements ToByteBuffer {
		private final PublicKey value;
		public PublicKeyToByteBuffer(PublicKey value) {
			this.value = value;
		}
		@Override
		public int size() {
			return Ints.BYTES + value.value.bytes.length;
		}
		@Override
		public void appendTo(ByteBuffer buffer) {
			buffer.putInt(value.value.bytes.length); //TODO Optimize size
			buffer.put(value.value.bytes);
		}
	}
	private static final class InstantToByteBuffer implements ToByteBuffer {
		private final Instant value;
		public InstantToByteBuffer(Instant value) {
			this.value = value;
		}
		@Override
		public int size() {
			return Longs.BYTES + Ints.BYTES;
		}
		@Override
		public void appendTo(ByteBuffer buffer) {
			buffer.putLong(value.getEpochSecond());
			buffer.putInt(value.getNano());
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
	public ByteBuffer serialize(Event event) {
		return Event.visit(event, new Event.Visitor<ByteBuffer>() {
    		@Override
    		public ByteBuffer visitOpenEvent(OpenEvent event) {
    			return build(
    				EventKind.OPEN,
					new PublicKeyToByteBuffer(event.account().key()),
					new LongToByteBuffer(event.signature()),
					new LongToByteBuffer(event.pow()),
					new InstantToByteBuffer(event.timestamp())
    			);
    		}
    		@Override
    		public ByteBuffer visitReceiveEvent(ReceiveEvent event) {
    			return build(
    				EventKind.RECEIVE,
					new LongToByteBuffer(event.previous().id()),
					new LongToByteBuffer(event.source().id()),
					new LongToByteBuffer(event.signature()),
					new LongToByteBuffer(event.pow()),
					new InstantToByteBuffer(event.timestamp())
    			);
    		}
    		@Override
    		public ByteBuffer visitSendEvent(SendEvent event) {
    			return build(
    				EventKind.SEND,
					new LongToByteBuffer(event.previous().id()),
					new PublicKeyToByteBuffer(event.from().key()),
					new PublicKeyToByteBuffer(event.to().key()),
					new LongToByteBuffer(event.amount().value()),
					new LongToByteBuffer(event.signature()),
					new LongToByteBuffer(event.pow()),
					new InstantToByteBuffer(event.timestamp())
    			);
    		}
    	});
	}
	
	private static Account accountFrom(ByteBuffer buffer) {
		int size = buffer.getInt();
		byte[] bytes = new byte[size]; //TODO Verif too big sizes
		buffer.get(bytes);
		return Account.Factory.account(PublicKey.Factory.publicKey(new ByteArray(bytes)));
	}
	
	@Override
	public Event deserialize(ByteBuffer buffer) {
		switch (EventKind.parse(buffer)) {
		case OPEN: {
			Account account = accountFrom(buffer);
			long signature = buffer.getLong();
			long pow = buffer.getLong();
			Instant timestamp = Instant.ofEpochSecond(buffer.getLong(), buffer.getInt());
			return Event.Factory.openEvent(account, pow, timestamp, signature);
		}
		case RECEIVE: {
			TxId previous = TxId.Factory.txId(buffer.getLong());
			TxId source = TxId.Factory.txId(buffer.getLong());
			long signature = buffer.getLong();
			long pow = buffer.getLong();
			Instant timestamp = Instant.ofEpochSecond(buffer.getLong(), buffer.getInt());
			return Event.Factory.receiveEvent(previous, source, pow, timestamp, signature);
		}
		case SEND: {
			TxId previous = TxId.Factory.txId(buffer.getLong());
			Account from = accountFrom(buffer);
			Account to = accountFrom(buffer);
			Amount amount = Amount.Factory.amount(buffer.getLong());
			long signature = buffer.getLong();
			long pow = buffer.getLong();
			Instant timestamp = Instant.ofEpochSecond(buffer.getLong(), buffer.getInt());
			return Event.Factory.sendEvent(previous, from, to, amount, pow, timestamp, signature);
		}
		default: {
			// Impossible case
			return null;
		}
		}
	}
}
