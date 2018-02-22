package com.github.itora.server.udp;

import java.time.Instant;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.davfx.ninio.core.Address;
import com.davfx.ninio.core.Ninio;
import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import com.github.itora.crypto.AsymmetricKey;
import com.github.itora.crypto.Cryptos;
import com.github.itora.crypto.Signed;
import com.github.itora.pow.Pow;
import com.github.itora.pow.Powed;
import com.github.itora.request.OpenRequest;
import com.github.itora.request.Request;
import com.github.itora.request.Requests;
import com.github.itora.request.SendRequest;
import com.github.itora.tx.AccountTxId;
import com.github.itora.tx.TxIds;

public class UdpServerTest {

	private Ninio ninio;
	
	private UdpServer leftUdpServer;
	private LinkedBlockingDeque<Signed<Powed<Request>>> leftRequests;
	private UdpServer rightUdpServer;
	private LinkedBlockingDeque<Signed<Powed<Request>>> rightRequests;
	
    @Before
    public void openServers() throws Exception {
    	ninio = Ninio.create();
    	
    	leftRequests = new LinkedBlockingDeque<>();
    	leftUdpServer = new UdpServer(ninio, 0, new Consumer<Signed<Powed<Request>>>() {
			@Override
			public void accept(Signed<Powed<Request>> request) {
				try {
					leftRequests.putLast(request);
				} catch (InterruptedException ie) {
				}
			}
		});
    	
    	rightRequests = new LinkedBlockingDeque<>();
    	rightUdpServer = new UdpServer(ninio, 0, new Consumer<Signed<Powed<Request>>>() {
			@Override
			public void accept(Signed<Powed<Request>> request) {
				try {
					rightRequests.putLast(request);
				} catch (InterruptedException ie) {
				}
			}
		});
    }
    
    @After
    public void closeServers() throws Exception {
    	leftUdpServer.close();
    	rightUdpServer.close();
    	ninio.close();
    }
    
    @Test
    public void shouldSendAndReceiveRequest() {
		AsymmetricKey keyEN = Cryptos.generate();
		AsymmetricKey keyS = Cryptos.generate();

		Account accountEN = Account.Factory.account(keyEN.publicKey());
		Account accountS = Account.Factory.account(keyS.publicKey());

		OpenRequest open = Request.Factory.openRequest(accountEN, Instant.EPOCH);
		SendRequest send = Request.Factory.sendRequest(AccountTxId.Factory.accountTxId(accountEN, TxIds.txId(open)),
				accountS, Amount.Factory.amount(30_000L), Instant.EPOCH.plusSeconds(200_000L));

    	Pow pow = Pow.Factory.pow(Cryptos.random(100));
    	Signed<Powed<Request>> sentRequest = Requests.sign(Powed.Factory.powed(send, pow), keyEN.privateKey());
		leftUdpServer.send(new Address(Address.LOCALHOST, rightUdpServer.port()), sentRequest);
		
		Signed<Powed<Request>> receivedRequest;
		try {
			receivedRequest = rightRequests.takeFirst();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
        Assertions.assertThat(Requests.verify(receivedRequest, keyEN.publicKey)).isTrue();
		Assertions.assertThat(receivedRequest.element().pow()).isEqualTo(pow);
		Assertions.assertThat(receivedRequest.element().element()).isEqualTo(send);
    }

}
