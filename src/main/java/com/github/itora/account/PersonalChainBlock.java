package com.github.itora.account;

import com.github.itora.amount.Amount;

public final class PersonalChainBlock {
	
	public final Amount amount;
	public final Account from;
	
	public PersonalChainBlock(Amount amount, Account from) {
		this.amount = amount;
		this.from = from;
	}
}
