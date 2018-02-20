package com.github.itora.event.internal;

public final class SerializationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SerializationException(String cause) {
		super(cause);
	}
	
	public SerializationException(Exception cause) {
		super(cause);
	}
}
