package com.github.itora.account;

import com.github.itora.crypto.PublicKey;
import com.github.itora.util.ByteArray;

public class Accounts {
	private Accounts() {
	}
	
	public static final Account EX_NIHILO = Account.Factory.account(PublicKey.Factory.publicKey(ByteArray.fromRepresentation("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKjhnkF7s9i1fZCHj+Wc+x0DdQM2nB/5w2l0ks5oLJVJhEqaWO+c6gZ+UiauWAKrX6iR5MJVawWxJ7OS1KaOjyECAwEAAQ==")));
}
