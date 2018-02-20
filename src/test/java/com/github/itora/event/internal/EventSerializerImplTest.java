package com.github.itora.event.internal;

import java.time.Instant;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import com.github.itora.crypto.AsymmetricKey;
import com.github.itora.crypto.AsymmetricKeys;
import com.github.itora.event.Event;
import com.github.itora.event.RegularEventSerializer;
import com.github.itora.event.OpenEvent;
import com.github.itora.event.ReceiveEvent;
import com.github.itora.event.SendEvent;
import com.github.itora.tx.TxIds;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class EventSerializerImplTest {

    private RegularEventSerializer eventSerializer;

    @Before
    public void setUp() throws Exception {
    	eventSerializer = new RegularEventSerializer();
    }

    private void shouldSerializeDeserialize(Event event) {
        Assertions.assertThat(eventSerializer.deserialize(eventSerializer.serialize(event))).isEqualTo(event);
    }

    @Test
    public void shouldSerializeDeserialize() {
        AsymmetricKey keyEN = AsymmetricKeys.generate();
        AsymmetricKey keyS = AsymmetricKeys.generate();

        Account accountEN = Account.Factory.account(keyEN.publicKey());
        Account accountS = Account.Factory.account(keyS.publicKey());

        OpenEvent open = Event.Factory.openEvent(accountEN, 0L, Instant.EPOCH);
        SendEvent send = Event.Factory.sendEvent(TxIds.txId(open), accountEN, accountS, Amount.Factory.amount(30_000L), 0L,
                Instant.EPOCH.plusSeconds(200_000L));
        ReceiveEvent receive = Event.Factory.receiveEvent(TxIds.txId(open), TxIds.txId(send),
                0L, Instant.EPOCH.plusSeconds(300_000L));
        
        shouldSerializeDeserialize(open);
        shouldSerializeDeserialize(send);
        shouldSerializeDeserialize(receive);

    }

}
