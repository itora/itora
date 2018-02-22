package com.github.itora.account.internal;

import java.util.function.Consumer;

import com.github.itora.account.Account;
import com.github.itora.account.RequestHandler;
import com.github.itora.account.SecureRequestHandler;
import com.github.itora.crypto.Cryptos;
import com.github.itora.crypto.Signed;
import com.github.itora.pow.Powed;
import com.github.itora.request.Request;
import com.github.itora.request.Requests;

public final class SecureRequestHandlerImpl implements SecureRequestHandler {

    private final RequestHandler requestHandler;

    public SecureRequestHandlerImpl(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void accept(Signed<Powed<Request>> signedRequest, Consumer<Request> withValidRequest) {
        Account emitter = Requests.emitter(signedRequest.powRequest.request);
        Cryptos.verify(signedRequest.signature, buffer, emitter.key);
    }

}
