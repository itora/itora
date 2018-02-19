package com.github.itora.account;

import com.github.itora.tx.Tx;

public final class Chain {
	public final Chain previous;
	public final Tx tx;

	public Chain(Chain previous, Tx tx) {
		this.previous = previous;
		this.tx = tx;
	}
}
