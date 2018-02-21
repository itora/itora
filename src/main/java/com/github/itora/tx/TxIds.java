package com.github.itora.tx;

import com.github.itora.crypto.Cryptos;
import com.github.itora.request.RegularRequestSerializer;
import com.github.itora.request.Request;

public final class TxIds {

    private TxIds() {
    }
    
    public static TxId txId(Request request) {
        return new TxId(Cryptos.hash(new RegularRequestSerializer().serialize(request)));
    }

}
