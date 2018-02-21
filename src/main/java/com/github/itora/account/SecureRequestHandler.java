package com.github.itora.account;

import com.github.itora.request.SignedPowRequest;

public interface SecureRequestHandler {

    void accept(SignedPowRequest signedRequest);

    default void accept(SignedPowRequest... signedRequests) {
        for (SignedPowRequest signedRequest : signedRequests) {
            accept(signedRequest);
        }
    }
}
