package com.github.itora.account.internal;

import java.util.Map;

import org.assertj.core.util.Maps;

import com.github.itora.account.Account;
import com.github.itora.account.AccountManager;
import com.github.itora.account.PersonalChain;
import com.github.itora.amount.Amount;
import com.github.itora.amount.Amounts;
import com.github.itora.event.Event;
import com.github.itora.event.OpenEvent;
import com.github.itora.event.ReceiveEvent;
import com.github.itora.event.SendEvent;
import com.github.itora.tx.OpenTx;
import com.github.itora.tx.ReceiveTx;
import com.github.itora.tx.SendTx;
import com.github.itora.tx.Tx;
import com.github.itora.tx.TxIds;

public final class AccountManagerImpl implements AccountManager {

	private final Map<Account, PersonalChain> personalChains = Maps.newHashMap();
	
	public AccountManagerImpl() {
	}
	
    public Amount balance(Account account) {
    	PersonalChain personalChain = personalChains.get(account);
    	Amount sum = Amounts.ZERO;
    	while (personalChain != null) {
        	sum = Amounts.plus(sum, Tx.visit(personalChain.tx, new Tx.Visitor<Amount>() {
        		@Override
        		public Amount visitOpenTx(OpenTx tx) {
        			return Amounts.ZERO;
        		}
        		@Override
        		public Amount visitReceiveTx(ReceiveTx tx) {
        			return tx.amount();
        		}
        		@Override
        		public Amount visitSendTx(SendTx tx) {
        			return Amounts.invers(tx.amount());
        		}
    		}));
    		personalChain = personalChain.previous;
    	}
        return sum;
    }

    @Override
    public void accept(Event event) {
    	// Check the event validity
    	Event.visit(event, new Event.Visitor<Void>() {
    		private void add(Account account, Tx tx) {
    	    	PersonalChain personalChain = personalChains.get(account);
    	    	personalChains.put(account, new PersonalChain(personalChain, tx));
    		}
    		@Override
    		public Void visitOpenEvent(OpenEvent event) {
    			Account account = event.account();
    	    	Tx tx = Tx.Factory.openTx(event.timestamp(), TxIds.txId(event));
    			add(account, tx);
    			return null;
    		}
    		@Override
    		public Void visitReceiveEvent(ReceiveEvent event) {
    			Account account = null; // TODO
    	    	Tx tx = Tx.Factory.receiveTx(TxIds.txId(event), event.amount(), event.timestamp());
    			add(account, tx);
    	    	return null;
    		}
    		@Override
    		public Void visitSendEvent(SendEvent event) {
    			Account account = event.from();
    	    	Tx tx = Tx.Factory.sendTx(TxIds.txId(event), event.to(), event.amount(), event.timestamp());
    			add(account, tx);
    			return null;
    		}
		});
    }
    
    @Override
    public Event generateSend(Amount amount, Account to) {
    	//TODO 
    	return null;
    }
}
