package com.github.itora.amount;

public final class Amounts {
	private Amounts() {
	}
	
	public static final Amount ZERO = new Amount(0L);
	
	public static Amount plus(Amount a, Amount b) {
		return new Amount(a.value + b.value);
	}

	public static Amount minus(Amount a, Amount b) {
		return new Amount(a.value - b.value);
	}
	
	public static boolean isStrictlyNegative(Amount a) {
	    return a.value < 0L;
	}
}
