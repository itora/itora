package com.github.itora.event;

import com.github.itora.crypto.AsymmetricKeys;
import com.github.itora.crypto.PrivateKey;
import com.github.itora.crypto.PublicKey;

public final class Events {
	private Events() {
	}
	
	public static SignedEvent sign(Event event, PrivateKey privateKey) {
        return new SignedEvent(event, AsymmetricKeys.sign(new RegularEventSerializer().serialize(event), privateKey));
	}

	public static void verify(SignedEvent event, PublicKey publicKey) {
        AsymmetricKeys.verify(event.signature(), new RegularEventSerializer().serialize(event.event()), publicKey);
	}
}
