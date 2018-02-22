package com.github.itora.util;

import java.security.SecureRandom;

public final class Undefineds {
	private Undefineds() {
	}
	
    // Allegedly thread-safe
    private static final SecureRandom RANDOM = new SecureRandom();

	public static final int HASH_CODE = RANDOM.nextInt();
}
