package com.github.itora.account.internal;

import java.time.Instant;
import java.util.Map;

import org.assertj.core.internal.cglib.core.Block;
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
    		private void add(Account account, Amount amount, Instant timestamp) {
    	    	PersonalChain personalChain = personalChains.get(account);
    	    	Tx tx = Tx.Factory.receiveTx(new TxId(), amount, timestamp);
    	    	personalChains.put(account, new PersonalChain(personalChain, tx));
    		}
    		@Override
    		public Void visitOpenEvent(OpenEvent event) {
    			return null;
    		}
    		@Override
    		public Void visitReceiveEvent(ReceiveEvent event) {
    			Account account;
    			add(account, event.amount(), event.timestamp());
    	    	return null;
    		}
    		@Override
    		public Void visitSendEvent(SendEvent event) {
    			//TODO
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
