package com.github.itora.account;

import java.util.function.Consumer;

import com.github.itora.crypto.Signed;
import com.github.itora.pocket.SignedPocket;
import com.github.itora.pow.Powed;
import com.github.itora.request.Request;

public interface SecureRequestHandler {

    void accept(Signed<Powed<Request>> signedRequest, Consumer<Request> withValidRequest);

    void accept(SignedPocket<Powed<Request>> signedPocket, Consumer<Request> withValidRequest);

    default void accept(Iterable<Signed<Powed<Request>>> signedRequests, Consumer<Request> withValidRequest) {
        for (Signed<Powed<Request>> signedRequest : signedRequests) {
            accept(signedRequest, withValidRequest);
        }
    }
}
