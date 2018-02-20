package com.github.itora.account;

import com.github.itora.amount.Amount;

public interface AccountManager extends EventHandler {

    Amount balance(Account account);

}
