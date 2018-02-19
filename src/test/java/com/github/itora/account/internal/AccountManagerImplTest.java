package com.github.itora.account.internal;

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

public class AccountManagerImplTest {

    private AccountManagerImpl accountManager;

    @Before
    public void setUp() throws Exception {
        accountManager = new AccountManagerImpl();
    }

    @Test
    public void shouldPlayScenario1() {
        Account accountEN = Account.Factory.account(0L);
        Account accountS = Account.Factory.account(1L);
        Account accountD = Account.Factory.account(2L);

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

}
