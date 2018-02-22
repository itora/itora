package com.github.itora.request;

import com.github.itora.account.Account;
import com.github.itora.crypto.Cryptos;
import com.github.itora.crypto.PrivateKey;
import com.github.itora.crypto.PublicKey;
import com.github.itora.crypto.Signed;
import com.github.itora.pow.Powed;

public final class Requests {
    private Requests() {
    }

    public static Signed<Powed<Request>> sign(Powed<Request> powRequest, PrivateKey privateKey) {
        return Signed.Factory.signed(powRequest, Cryptos.sign(new RegularRequestSerializer().serialize(powRequest.element), privateKey));
    }

    public static boolean verify(Signed<Powed<Request>> request, PublicKey publicKey) {
        return Cryptos.verify(request.signature(), new RegularRequestSerializer().serialize(request.element().element()), publicKey);
    }
    
    public static Account emitter(Request request) {
        return Request.visit(request, new Request.Visitor<Account>() {

            @Override
            public Account visitOpenRequest(OpenRequest openRequest) {
                return openRequest.account;
            }

            @Override
            public Account visitSendRequest(SendRequest sendRequest) {
                return sendRequest.previous.account;
            }

            @Override
            public Account visitReceiveRequest(ReceiveRequest receiveRequest) {
                return receiveRequest.previous.account;
            }
        });
    }
}
