package com.github.itora.tx;

import com.github.itora.crypto.Hashes;
import com.github.itora.event.Event;
import com.github.itora.event.RegularEventSerializer;

public final class TxIds {

    private TxIds() {
    }
    
    public static TxId txId(Event event) {
        return new TxId(Hashes.hash(new RegularEventSerializer().serialize(event)));
    }

}
