package com.github.itora.tx;

import com.github.itora.crypto.AsymmetricKeys;
import com.github.itora.request.RegularRequestSerializer;
import com.github.itora.request.Request;

public final class TxIds {

    private TxIds() {
    }
    
    public static TxId txId(Request request) {
        return new TxId(AsymmetricKeys.hash(new RegularRequestSerializer().serialize(request)));
    }

}
