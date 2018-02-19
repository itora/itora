package com.github.itora.account;

import com.github.itora.event.Event;

public interface AccountManager {

    Amount balance(Account account);
    
    void accept(Event event);
    
    Event generateSend(Amount amount, Account to);
}
