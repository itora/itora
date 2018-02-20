package com.github.itora.event.internal;

import java.time.Instant;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import com.github.itora.event.Event;
import com.github.itora.event.OpenEvent;
import com.github.itora.event.ReceiveEvent;
import com.github.itora.event.SendEvent;
import com.github.itora.tx.TxIds;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class EventSerializerImplTest {

    private EventSerializerImpl eventSerializer;

    @Before
    public void setUp() throws Exception {
    	eventSerializer = new EventSerializerImpl();
    }

    private void shouldSerializeDeserialize(Event event) {
        Assertions.assertThat(eventSerializer.deserialize(eventSerializer.serialize(event))).isEqualTo(event);
    }

    @Test
    public void shouldSerializeDeserialize() {
        Account accountEN = Account.Factory.account(0L);
        Account accountS = Account.Factory.account(1L);

        OpenEvent open = Event.Factory.openEvent(accountEN, 0L, Instant.EPOCH, 0L);
        SendEvent send = Event.Factory.sendEvent(TxIds.txId(open), accountEN, accountS, Amount.Factory.amount(30_000L), 0L,
                Instant.EPOCH.plusSeconds(200_000L), 0L);
        ReceiveEvent receive = Event.Factory.receiveEvent(TxIds.txId(open), TxIds.txId(send),
                0L, Instant.EPOCH.plusSeconds(300_000L), 0L);
        
        shouldSerializeDeserialize(open);
        shouldSerializeDeserialize(send);
        shouldSerializeDeserialize(receive);

    }

}
