package com.github.itora.account;

import com.github.itora.amount.Amount;
import com.github.itora.event.Event;

public interface AccountManager {

    Amount balance(Account account);

    void accept(Event event);

    default void accept(Event... events) {
        for (Event event : events) {
            accept(event);
        }
    }
}
