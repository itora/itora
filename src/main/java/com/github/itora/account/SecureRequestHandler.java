package com.github.itora.account;

import java.util.function.Consumer;

import com.github.itora.request.Request;
import com.github.itora.request.SignedPowRequest;

public interface SecureRequestHandler {

    void accept(SignedPowRequest signedRequest, Consumer<Request> withValidRequest);

    default void accept(Iterable<SignedPowRequest> signedRequests, Consumer<Request> withValidRequest) {
        for (SignedPowRequest signedRequest : signedRequests) {
            accept(signedRequest, withValidRequest);
        }
    }
}
