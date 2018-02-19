package com.github.itora.account;

public final class PersonalChainBlock {
	
	public final Amount amount;
	public final Account from;
	
	public PersonalChainBlock(Amount amount, Account from) {
		this.amount = amount;
		this.from = from;
	}
}
