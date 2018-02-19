package com.github.itora.account;

public interface AccountManager {

    Amount balance(Account account);
    
    void accept(Event event);
    
    Event generateSend(Amount amount, Account to);
}
