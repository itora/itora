package com.github.itora.account.internal;

import java.time.Instant;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import com.github.itora.bootstrap.LatticeBootstrap;
import com.github.itora.crypto.AsymmetricKey;
import com.github.itora.crypto.AsymmetricKeys;
import com.github.itora.request.OpenRequest;
import com.github.itora.request.ReceiveRequest;
import com.github.itora.request.Request;
import com.github.itora.request.SendRequest;
import com.github.itora.tx.AccountTxId;
import com.github.itora.tx.TxIds;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class AccountManagerImplTest {

    private AccountManagerImpl accountManager;
    private Account accountEN;
    private Account accountS;
    private Account accountD;

    @Before
    public void setUp() throws Exception {
        accountManager = new AccountManagerImpl(LatticeBootstrap.EMPTY);

        AsymmetricKey keyEN = AsymmetricKeys.generate();
        AsymmetricKey keyS = AsymmetricKeys.generate();
        AsymmetricKey keyD = AsymmetricKeys.generate();

        accountEN = Account.Factory.account(keyEN.publicKey());
        accountS = Account.Factory.account(keyS.publicKey());
        accountD = Account.Factory.account(keyD.publicKey());
    }

    @Test
    public void shouldPlayScenario1() {
        OpenRequest openEN = Request.Factory.openRequest(accountEN, Instant.EPOCH);
        OpenRequest openS = Request.Factory.openRequest(accountS, Instant.EPOCH);
        OpenRequest openD = Request.Factory.openRequest(accountD, Instant.EPOCH.plusSeconds(86_400L));

        accountManager.accept(openEN, openS, openD);

        SendRequest sendENtoS = Request.Factory.sendRequest(TxIds.txId(openEN), accountEN, accountS, Amount.Factory.amount(30_000L),
                Instant.EPOCH.plusSeconds(200_000L));
        ReceiveRequest receiveSfromEN = Request.Factory.receiveRequest(TxIds.txId(openS),
                AccountTxId.Factory.accountTxId(accountEN, TxIds.txId(sendENtoS)),
                Instant.EPOCH.plusSeconds(300_000L));

        accountManager.accept(sendENtoS, receiveSfromEN);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(30_000L));

        SendRequest sendStoD = Request.Factory.sendRequest(TxIds.txId(receiveSfromEN), accountS, accountD, Amount.Factory.amount(5_000L),

                Instant.EPOCH.plusSeconds(400_000L));

        accountManager.accept(sendStoD);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(25_000L));
        Assertions.assertThat(accountManager.balance(accountD)).isEqualTo(Amount.Factory.amount(0L));

        ReceiveRequest receiveDfromS = Request.Factory.receiveRequest(TxIds.txId(openD),
                AccountTxId.Factory.accountTxId(accountS, TxIds.txId(sendStoD)),
                Instant.EPOCH.plusSeconds(500_000L));

        accountManager.accept(receiveDfromS);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(25_000L));
        Assertions.assertThat(accountManager.balance(accountD)).isEqualTo(Amount.Factory.amount(5_000L));
    }

    @Test
    public void shouldIgnoreRequestsAlreadyProcessed() {
        OpenRequest openEN = Request.Factory.openRequest(accountEN, Instant.EPOCH);
        OpenRequest openS = Request.Factory.openRequest(accountS, Instant.EPOCH);
        OpenRequest openD = Request.Factory.openRequest(accountD, Instant.EPOCH.plusSeconds(86_400L));

        accountManager.accept(openEN, openS, openD);

        SendRequest sendENtoS = Request.Factory.sendRequest(TxIds.txId(openEN), accountEN, accountS, Amount.Factory.amount(30_000L),
                Instant.EPOCH.plusSeconds(200_000L));
        ReceiveRequest receiveSfromEN = Request.Factory.receiveRequest(TxIds.txId(openS),
                AccountTxId.Factory.accountTxId(accountEN, TxIds.txId(sendENtoS)),
                Instant.EPOCH.plusSeconds(300_000L));

        accountManager.accept(sendENtoS, receiveSfromEN);

        accountManager.accept(openS);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(30_000L));

        SendRequest sendStoD = Request.Factory.sendRequest(TxIds.txId(receiveSfromEN), accountS, accountD, Amount.Factory.amount(5_000L),

                Instant.EPOCH.plusSeconds(400_000L));

        accountManager.accept(sendStoD);
        accountManager.accept(sendStoD);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(25_000L));
        Assertions.assertThat(accountManager.balance(accountD)).isEqualTo(Amount.Factory.amount(0L));

        ReceiveRequest receiveDfromS = Request.Factory.receiveRequest(TxIds.txId(openD),
                AccountTxId.Factory.accountTxId(accountS, TxIds.txId(sendStoD)),
                Instant.EPOCH.plusSeconds(500_000L));

        accountManager.accept(receiveDfromS);
        accountManager.accept(receiveDfromS);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(25_000L));
        Assertions.assertThat(accountManager.balance(accountD)).isEqualTo(Amount.Factory.amount(5_000L));

    }

    @Test
    public void shouldDetectDoubleSpend() {
        OpenRequest openEN = Request.Factory.openRequest(accountEN, Instant.EPOCH);
        OpenRequest openS = Request.Factory.openRequest(accountS, Instant.EPOCH);
        OpenRequest openD = Request.Factory.openRequest(accountD, Instant.EPOCH.plusSeconds(86_400L));

        accountManager.accept(openEN, openS, openD);

        SendRequest sendENtoS = Request.Factory.sendRequest(TxIds.txId(openEN), accountEN, accountS, Amount.Factory.amount(30_000L),
                Instant.EPOCH.plusSeconds(200_000L));
        ReceiveRequest receiveSfromEN = Request.Factory.receiveRequest(TxIds.txId(openS),
                AccountTxId.Factory.accountTxId(accountEN, TxIds.txId(sendENtoS)),
                Instant.EPOCH.plusSeconds(300_000L));

        accountManager.accept(sendENtoS, receiveSfromEN);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(30_000L));

        SendRequest sendStoD1 = Request.Factory.sendRequest(TxIds.txId(receiveSfromEN), accountS, accountD, Amount.Factory.amount(5_000L),

                Instant.EPOCH.plusSeconds(400_000L));
        SendRequest sendStoD2 = Request.Factory.sendRequest(TxIds.txId(receiveSfromEN), accountS, accountD, Amount.Factory.amount(6_000L),

                Instant.EPOCH.plusSeconds(400_000L));

        accountManager.accept(sendStoD1);
        accountManager.accept(sendStoD2);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(25_000L));
        Assertions.assertThat(accountManager.balance(accountD)).isEqualTo(Amount.Factory.amount(0L));

        ReceiveRequest receiveDfromS = Request.Factory.receiveRequest(TxIds.txId(openD),
                AccountTxId.Factory.accountTxId(accountS, TxIds.txId(sendStoD1)),
                Instant.EPOCH.plusSeconds(500_000L));

        accountManager.accept(receiveDfromS);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(25_000L));
        Assertions.assertThat(accountManager.balance(accountD)).isEqualTo(Amount.Factory.amount(5_000L));
    }

}
