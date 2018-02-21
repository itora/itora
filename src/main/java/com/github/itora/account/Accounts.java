package com.github.itora.account;

import com.github.itora.crypto.PublicKey;
import com.github.itora.util.ByteArray;
import com.google.common.io.BaseEncoding;

public class Accounts {
	private Accounts() {
	}
	
	public static final Account EX_NIHILO = Account.Factory.account(PublicKey.Factory.publicKey(new ByteArray(BaseEncoding.base64().decode("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKjhnkF7s9i1fZCHj+Wc+x0DdQM2nB/5w2l0ks5oLJVJhEqaWO+c6gZ+UiauWAKrX6iR5MJVawWxJ7OS1KaOjyECAwEAAQ=="))));
}
