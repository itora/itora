package com.github.itora.account;

public interface AccountManager {

    long balance(Account account);
    
    void accept(Event event);
}
