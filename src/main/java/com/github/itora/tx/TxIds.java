package com.github.itora.tx;

import com.github.itora.event.Event;

public final class TxIds {

    private TxIds() {
    }
    
    public static TxId txId(Event event) {
        return new TxId(event.hashCode());
    }

}
