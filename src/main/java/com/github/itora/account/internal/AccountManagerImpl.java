package com.github.itora.account.internal;

import java.util.Map;
import java.util.Optional;

import com.github.itora.account.Account;
import com.github.itora.account.AccountManager;
import com.github.itora.account.Chain;
import com.github.itora.account.Chains;
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
import com.github.itora.tx.TxId;
import com.github.itora.tx.TxIds;
import com.google.common.collect.Maps;

public final class AccountManagerImpl implements AccountManager {

	private final Map<Account, Chain> personalChains = Maps.newHashMap();
	
	public AccountManagerImpl() {
	}
	
    public Amount balance(Account account) {
    	Amount sum = Amounts.ZERO;
    	for (Tx tx : Chains.iterate(personalChains.get(account))) {
        	sum = Amounts.plus(sum, Tx.visit(tx, new Tx.Visitor<Amount>() {
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
		}
        return sum;
    }

    @Override
    public void accept(Event event) {
    	// Check the event validity
    	Event.visit(event, new Event.Visitor<Void>() {
    		private void add(Account account, Tx tx) {
    	    	Chain personalChain = personalChains.get(account);
    	    	personalChains.put(account, new Chain(personalChain, tx));
    		}
    		@Override
    		public Void visitOpenEvent(OpenEvent event) {
    			Account account = event.account();
    	    	Tx tx = Tx.Factory.openTx(TxIds.txId(event), event.timestamp());
    			add(account, tx);
    			return null;
    		}
    		@Override
    		public Void visitReceiveEvent(ReceiveEvent event) {
    			TxId sourceTx = event.source();
    			Account account = null;
    			Amount amount = null;
    			for (Map.Entry<Account, Chain> entry : personalChains.entrySet()) {
        	    	for (Tx tx : Chains.iterate(entry.getValue())) {
        	    		if (sourceTx.equals(tx.txId())) {
        	    			account = entry.getKey();
        	    			
        	    			Optional<Amount> a = Tx.visit(tx, new Tx.Visitor<Optional<Amount>>() {
        	            		@Override
        	            		public Optional<Amount> visitOpenTx(OpenTx tx) {
        	            			return Optional.empty();
        	            		}
        	            		@Override
        	            		public Optional<Amount> visitReceiveTx(ReceiveTx tx) {
        	            			return Optional.empty();
        	            		}
        	            		@Override
        	            		public Optional<Amount> visitSendTx(SendTx tx) {
        	            			return Optional.of(tx.amount);
        	            		}
        	        		});

        	    			amount = a.get();
        	    			break;
        	    		}
        	    	}
        	    	if (account != null) {
        	    		break;
        	    	}
    			}
    	    	Tx tx = Tx.Factory.receiveTx(TxIds.txId(event), amount, event.timestamp());
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
}
