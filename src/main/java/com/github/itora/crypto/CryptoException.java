package com.github.itora.crypto;

public final class CryptoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CryptoException(String cause) {
		super(cause);
	}
	
	public CryptoException(Exception cause) {
		super(cause);
	}
}
