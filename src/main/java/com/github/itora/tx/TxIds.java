package com.github.itora.tx;

import com.github.itora.crypto.AsymmetricKeys;
import com.github.itora.event.Event;
import com.github.itora.event.RegularEventSerializer;

public final class TxIds {

    private TxIds() {
    }
    
    public static TxId txId(Event event) {
        return new TxId(AsymmetricKeys.hash(new RegularEventSerializer().serialize(event)));
    }

}
