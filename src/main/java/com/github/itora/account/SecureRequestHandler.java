package com.github.itora.account;

import com.github.itora.request.SignedRequest;

public interface SecureRequestHandler {

    void accept(SignedRequest signedRequest);

    default void accept(SignedRequest... signedRequests) {
        for (SignedRequest signedRequest : signedRequests) {
            accept(signedRequest);
        }
    }
}
