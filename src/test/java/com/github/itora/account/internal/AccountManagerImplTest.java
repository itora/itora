package com.github.itora.account.internal;

import java.time.Instant;

import com.github.itora.account.Account;
import com.github.itora.amount.Amount;
import com.github.itora.bootstrap.LatticeBootstrap;
import com.github.itora.crypto.AsymmetricKey;
import com.github.itora.crypto.AsymmetricKeys;
import com.github.itora.event.Event;
import com.github.itora.event.OpenEvent;
import com.github.itora.event.ReceiveEvent;
import com.github.itora.event.SendEvent;
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
        OpenEvent openEN = Event.Factory.openEvent(accountEN, 0L, Instant.EPOCH, 0L);
        OpenEvent openS = Event.Factory.openEvent(accountS, 0L, Instant.EPOCH, 0L);
        OpenEvent openD = Event.Factory.openEvent(accountD, 0L, Instant.EPOCH.plusSeconds(86_400L), 0L);

        accountManager.accept(openEN, openS, openD);

        SendEvent sendENtoS = Event.Factory.sendEvent(TxIds.txId(openEN), accountEN, accountS, Amount.Factory.amount(30_000L), 0L,
                Instant.EPOCH.plusSeconds(200_000L), 0L);
        ReceiveEvent receiveSfromEN = Event.Factory.receiveEvent(TxIds.txId(openS), TxIds.txId(sendENtoS),
                0L, Instant.EPOCH.plusSeconds(300_000L), 0L);

        accountManager.accept(sendENtoS, receiveSfromEN);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(30_000L));

        SendEvent sendStoD = Event.Factory.sendEvent(TxIds.txId(receiveSfromEN), accountS, accountD, Amount.Factory.amount(5_000L), 0L,
                Instant.EPOCH.plusSeconds(400_000L), 0L);

        accountManager.accept(sendStoD);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(25_000L));
        Assertions.assertThat(accountManager.balance(accountD)).isEqualTo(Amount.Factory.amount(0L));

        ReceiveEvent receiveDfromS = Event.Factory.receiveEvent(TxIds.txId(openD), TxIds.txId(sendStoD),
                0L, Instant.EPOCH.plusSeconds(500_000L), 0L);

        accountManager.accept(receiveDfromS);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(25_000L));
        Assertions.assertThat(accountManager.balance(accountD)).isEqualTo(Amount.Factory.amount(5_000L));
    }

    @Test
    public void shouldIgnoreEventsAlreadyProcessed() {
        OpenEvent openEN = Event.Factory.openEvent(accountEN, 0L, Instant.EPOCH, 0L);
        OpenEvent openS = Event.Factory.openEvent(accountS, 0L, Instant.EPOCH, 0L);
        OpenEvent openD = Event.Factory.openEvent(accountD, 0L, Instant.EPOCH.plusSeconds(86_400L), 0L);

        accountManager.accept(openEN, openS, openD);

        SendEvent sendENtoS = Event.Factory.sendEvent(TxIds.txId(openEN), accountEN, accountS, Amount.Factory.amount(30_000L), 0L,
                Instant.EPOCH.plusSeconds(200_000L), 0L);
        ReceiveEvent receiveSfromEN = Event.Factory.receiveEvent(TxIds.txId(openS), TxIds.txId(sendENtoS),
                0L, Instant.EPOCH.plusSeconds(300_000L), 0L);

        accountManager.accept(sendENtoS, receiveSfromEN);
        
        accountManager.accept(openS);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(30_000L));

        SendEvent sendStoD = Event.Factory.sendEvent(TxIds.txId(receiveSfromEN), accountS, accountD, Amount.Factory.amount(5_000L), 0L,
                Instant.EPOCH.plusSeconds(400_000L), 0L);

        accountManager.accept(sendStoD);
        accountManager.accept(sendStoD);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(25_000L));
        Assertions.assertThat(accountManager.balance(accountD)).isEqualTo(Amount.Factory.amount(0L));

        ReceiveEvent receiveDfromS = Event.Factory.receiveEvent(TxIds.txId(openD), TxIds.txId(sendStoD),
                0L, Instant.EPOCH.plusSeconds(500_000L), 0L);

        accountManager.accept(receiveDfromS);
        accountManager.accept(receiveDfromS);


        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(25_000L));
        Assertions.assertThat(accountManager.balance(accountD)).isEqualTo(Amount.Factory.amount(5_000L));

    }
    
    @Test
    public void shouldDetectDoubleSpend() {
        OpenEvent openEN = Event.Factory.openEvent(accountEN, 0L, Instant.EPOCH, 0L);
        OpenEvent openS = Event.Factory.openEvent(accountS, 0L, Instant.EPOCH, 0L);
        OpenEvent openD = Event.Factory.openEvent(accountD, 0L, Instant.EPOCH.plusSeconds(86_400L), 0L);

        accountManager.accept(openEN, openS, openD);

        SendEvent sendENtoS = Event.Factory.sendEvent(TxIds.txId(openEN), accountEN, accountS, Amount.Factory.amount(30_000L), 0L,
                Instant.EPOCH.plusSeconds(200_000L), 0L);
        ReceiveEvent receiveSfromEN = Event.Factory.receiveEvent(TxIds.txId(openS), TxIds.txId(sendENtoS),
                0L, Instant.EPOCH.plusSeconds(300_000L), 0L);

        accountManager.accept(sendENtoS, receiveSfromEN);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(30_000L));

        SendEvent sendStoD1 = Event.Factory.sendEvent(TxIds.txId(receiveSfromEN), accountS, accountD, Amount.Factory.amount(5_000L), 0L,
                Instant.EPOCH.plusSeconds(400_000L), 0L);
        SendEvent sendStoD2 = Event.Factory.sendEvent(TxIds.txId(receiveSfromEN), accountS, accountD, Amount.Factory.amount(6_000L), 0L,
                Instant.EPOCH.plusSeconds(400_000L), 0L);

        
        accountManager.accept(sendStoD1);
        accountManager.accept(sendStoD2);


        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(25_000L));
        Assertions.assertThat(accountManager.balance(accountD)).isEqualTo(Amount.Factory.amount(0L));

        ReceiveEvent receiveDfromS = Event.Factory.receiveEvent(TxIds.txId(openD), TxIds.txId(sendStoD1),
                0L, Instant.EPOCH.plusSeconds(500_000L), 0L);

        accountManager.accept(receiveDfromS);

        Assertions.assertThat(accountManager.balance(accountS)).isEqualTo(Amount.Factory.amount(25_000L));
        Assertions.assertThat(accountManager.balance(accountD)).isEqualTo(Amount.Factory.amount(5_000L));   
    }

}
