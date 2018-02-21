package com.github.itora.account.internal;

import com.github.itora.account.RequestHandler;
import com.github.itora.account.SecureRequestHandler;
import com.github.itora.request.SignedRequest;

public final class SecureRequestHandlerImpl implements SecureRequestHandler {

    private final RequestHandler requestHandler;

    public SecureRequestHandlerImpl(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void accept(SignedRequest signedRequest) {
        requestHandler.accept(signedRequest.request());
    }

}
