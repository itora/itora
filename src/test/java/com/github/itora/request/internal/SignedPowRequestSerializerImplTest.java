package com.github.itora.request.internal;

import java.time.Instant;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import com.github.itora.crypto.AsymmetricKey;
import com.github.itora.crypto.AsymmetricKeys;
import com.github.itora.request.OpenRequest;
import com.github.itora.request.ReceiveRequest;
import com.github.itora.request.RegularSignedPowRequestSerializer;
import com.github.itora.request.Request;
import com.github.itora.request.Requests;
import com.github.itora.request.SendRequest;
import com.github.itora.request.SignedPowRequest;
import com.github.itora.tx.AccountTxId;
import com.github.itora.tx.TxIds;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class SignedPowRequestSerializerImplTest {

    private RegularSignedPowRequestSerializer requestSerializer;

    @Before
    public void setUp() throws Exception {
        requestSerializer = new RegularSignedPowRequestSerializer();
    }

    private void shouldSerializeDeserialize(Request request, AsymmetricKey k) {
        SignedPowRequest se = Requests.sign(request, k.privateKey());
        SignedPowRequest see = requestSerializer.deserialize(requestSerializer.serialize(se));
        Requests.verify(see, k.publicKey);
        Assertions.assertThat(see).isEqualTo(se);
    }

    @Test
    public void shouldSerializeDeserialize() {
        AsymmetricKey keyEN = AsymmetricKeys.generate();
        AsymmetricKey keyS = AsymmetricKeys.generate();

        Account accountEN = Account.Factory.account(keyEN.publicKey());
        Account accountS = Account.Factory.account(keyS.publicKey());

        OpenRequest open = Request.Factory.openRequest(accountEN, Instant.EPOCH);
        SendRequest send = Request.Factory.sendRequest(TxIds.txId(open), accountEN, accountS, Amount.Factory.amount(30_000L),
                Instant.EPOCH.plusSeconds(200_000L));
        ReceiveRequest receive = Request.Factory.receiveRequest(TxIds.txId(open),
                AccountTxId.Factory.accountTxId(accountEN, TxIds.txId(send)),
                Instant.EPOCH.plusSeconds(300_000L));

        shouldSerializeDeserialize(open, keyEN);
        shouldSerializeDeserialize(send, keyEN);
        shouldSerializeDeserialize(receive, keyS);

    }

}
