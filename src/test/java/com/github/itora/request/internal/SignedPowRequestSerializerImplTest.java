package com.github.itora.request.internal;

import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import com.github.itora.crypto.AsymmetricKey;
import com.github.itora.crypto.Cryptos;
import com.github.itora.crypto.Signed;
import com.github.itora.pow.Pow;
import com.github.itora.pow.Powed;
import com.github.itora.request.OpenRequest;
import com.github.itora.request.ReceiveRequest;
import com.github.itora.request.RegularSignedPowRequestSerializer;
import com.github.itora.request.Request;
import com.github.itora.request.Requests;
import com.github.itora.request.SendRequest;
import com.github.itora.tx.AccountTxId;
import com.github.itora.tx.TxIds;

public class SignedPowRequestSerializerImplTest {

    private RegularSignedPowRequestSerializer requestSerializer;

    @Before
    public void setUp() throws Exception {
        requestSerializer = new RegularSignedPowRequestSerializer();
    }

    private void shouldSerializeDeserialize(Request request, AsymmetricKey k) {
    	Pow pow = Pow.Factory.pow(Cryptos.random(100));
    	Powed<Request> pr = Powed.Factory.powed(request, pow);
        Signed<Powed<Request>> spr = Requests.sign(pr, k.privateKey());
        Signed<Powed<Request>> result = requestSerializer.deserialize(requestSerializer.serialize(spr));
        Assertions.assertThat(Requests.verify(result, k.publicKey)).isTrue();
        Assertions.assertThat(result).isEqualTo(spr);
    }

    @Test
    public void shouldSerializeDeserialize() {
        AsymmetricKey keyEN = Cryptos.generate();
        AsymmetricKey keyS = Cryptos.generate();

        Account accountEN = Account.Factory.account(keyEN.publicKey());
        Account accountS = Account.Factory.account(keyS.publicKey());

		OpenRequest open = Request.Factory.openRequest(accountEN, Instant.EPOCH);
		SendRequest send = Request.Factory.sendRequest(AccountTxId.Factory.accountTxId(accountEN, TxIds.txId(open)),
				accountS, Amount.Factory.amount(30_000L), Instant.EPOCH.plusSeconds(200_000L));
		ReceiveRequest receive = Request.Factory.receiveRequest(
				AccountTxId.Factory.accountTxId(accountS, TxIds.txId(open)),
				AccountTxId.Factory.accountTxId(accountEN, TxIds.txId(send)), Instant.EPOCH.plusSeconds(300_000L));

        shouldSerializeDeserialize(open, keyEN);
        shouldSerializeDeserialize(send, keyEN);
        shouldSerializeDeserialize(receive, keyS);

    }

}
