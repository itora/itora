package com.github.itora.account.internal;

import java.util.List;

import com.github.itora.account.Account;
import com.github.itora.account.AccountManager;
import com.github.itora.account.Amount;
import com.github.itora.account.PersonalChainBlock;
import com.github.itora.event.Event;
import com.google.common.collect.Lists;

public final class AccountManagerImpl implements AccountManager {

	private final List<PersonalChainBlock> personalChain = Lists.newArrayList();
	
	public AccountManagerImpl() {
	}
	
    public Amount balance(Account account) {
    	long sum = 0L;
    	for (PersonalChainBlock b : personalChain) {
    		sum += b.amount.delta;
    	}
        return new Amount(sum);
    }

    @Override
    public void accept(Event event) {
    	// Check the event validity
    	
    	// Push the event 'raw data' to the personal chain
    	PersonalChainBlock block = new PersonalChainBlock(event.amount(), event.from());
    	personalChain.add(block);
    }
    
    @Override
    public Event generateSend(Amount amount, Account to) {
    	//TODO 
    	return null;
    }
}
