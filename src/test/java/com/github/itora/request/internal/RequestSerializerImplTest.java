package com.github.itora.request.internal;

import java.time.Instant;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import com.github.itora.crypto.AsymmetricKey;
import com.github.itora.crypto.AsymmetricKeys;
import com.github.itora.request.OpenRequest;
import com.github.itora.request.ReceiveRequest;
import com.github.itora.request.RegularRequestSerializer;
import com.github.itora.request.Request;
import com.github.itora.request.SendRequest;
import com.github.itora.tx.AccountTxId;
import com.github.itora.tx.TxIds;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class RequestSerializerImplTest {

    private RegularRequestSerializer requestSerializer;

    @Before
    public void setUp() throws Exception {
    	requestSerializer = new RegularRequestSerializer();
    }

    private void shouldSerializeDeserialize(Request request) {
        Assertions.assertThat(requestSerializer.deserialize(requestSerializer.serialize(request))).isEqualTo(request);
    }

    @Test
    public void shouldSerializeDeserialize() {
        AsymmetricKey keyEN = AsymmetricKeys.generate();
        AsymmetricKey keyS = AsymmetricKeys.generate();

        Account accountEN = Account.Factory.account(keyEN.publicKey());
        Account accountS = Account.Factory.account(keyS.publicKey());

        OpenRequest open = Request.Factory.openRequest(accountEN, 0L, Instant.EPOCH);
        SendRequest send = Request.Factory.sendRequest(TxIds.txId(open), accountEN, accountS, Amount.Factory.amount(30_000L), 0L,
                Instant.EPOCH.plusSeconds(200_000L));
        ReceiveRequest receive = Request.Factory.receiveRequest(TxIds.txId(open),
                AccountTxId.Factory.accountTxId(accountEN, TxIds.txId(send)),
                0L, Instant.EPOCH.plusSeconds(300_000L));
        
        shouldSerializeDeserialize(open);
        shouldSerializeDeserialize(send);
        shouldSerializeDeserialize(receive);

    }

}
