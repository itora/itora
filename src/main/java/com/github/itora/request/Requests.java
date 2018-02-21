package com.github.itora.request;

import com.github.itora.crypto.AsymmetricKeys;
import com.github.itora.crypto.PrivateKey;
import com.github.itora.crypto.PublicKey;

public final class Requests {
    private Requests() {
    }

    public static SignedPowRequest sign(PowRequest powRequest, PrivateKey privateKey) {
        return SignedPowRequest.Factory.signedPowRequest(powRequest, AsymmetricKeys.sign(new RegularRequestSerializer().serialize(powRequest.request()), privateKey));
    }

    public static boolean verify(SignedPowRequest request, PublicKey publicKey) {
        return AsymmetricKeys.verify(request.signature(), new RegularRequestSerializer().serialize(request.powRequest().request()), publicKey);
    }
}
