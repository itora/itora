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
    
    private final static Request.Visitor<Account> EMITTER_VISITOR = new Request.Visitor<Account>() {
        public Account visitOpenRequest(OpenRequest openRequest) {
            return openRequest.account;
        }
        public Account visitSendRequest(SendRequest sendRequest) {
            return sendRequest.previous.account;
        }
        public Account visitReceiveRequest(ReceiveRequest receiveRequest) {
            return receiveRequest.previous.account;
        }
    };

    //TODO de la merde
    public static Signed<Powed<Request>> sign(Powed<Request> powRequest, PrivateKey privateKey) {
        return Signed.Factory.signed(powRequest, Cryptos.sign(new RegularRequestSerializer().serialize(powRequest.element()), privateKey));
    }

    //TODO de la merde
    public static boolean verify(Signed<Powed<Request>> request, PublicKey publicKey) {
        return Cryptos.verify(request.signature(), new RegularRequestSerializer().serialize(request.element().element()), publicKey);
    }
    
    public static Account emitter(Request request) {
        return Request.visit(request, EMITTER_VISITOR);
    }
}
