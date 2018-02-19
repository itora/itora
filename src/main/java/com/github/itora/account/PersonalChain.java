package com.github.itora.account;

import com.github.itora.tx.Tx;

public final class PersonalChain {
	public final PersonalChain previous;
	public final Tx tx;

	public PersonalChain(PersonalChain previous, Tx tx) {
		this.previous = previous;
		this.tx = tx;
	}
}
