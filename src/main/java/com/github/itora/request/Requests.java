package com.github.itora.request;

import com.github.itora.crypto.AsymmetricKeys;
import com.github.itora.crypto.PrivateKey;
import com.github.itora.crypto.PublicKey;

public final class Requests {
    private Requests() {
    }

    public static SignedRequest sign(Request request, PrivateKey privateKey) {
        return new SignedRequest(request, AsymmetricKeys.sign(new RegularRequestSerializer().serialize(request), privateKey));
    }

    public static void verify(SignedRequest request, PublicKey publicKey) {
        AsymmetricKeys.verify(request.signature(), new RegularRequestSerializer().serialize(request.request()), publicKey);
    }
}
