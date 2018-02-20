package com.github.itora.account;

import com.github.itora.event.SignedEvent;

public interface SecureEventHandler {

    void accept(SignedEvent signedEvent);

    default void accept(SignedEvent... signedEvents) {
        for (SignedEvent signedEvent : signedEvents) {
            accept(signedEvent);
        }
    }
}
