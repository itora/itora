package com.github.itora.account.internal;

import com.github.itora.account.EventHandler;
import com.github.itora.account.SecureEventHandler;
import com.github.itora.event.SignedEvent;

public final class SecureAccountManagerImpl implements SecureEventHandler {

    private final EventHandler eventHandler;

    public SecureAccountManagerImpl(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void accept(SignedEvent signedEvent) {
        eventHandler.accept(signedEvent.event());
    }

}
