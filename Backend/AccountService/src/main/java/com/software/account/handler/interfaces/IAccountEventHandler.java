package com.software.account.handler.interfaces;

import com.software.account.event.AccountActivatedEvent;
import com.software.account.event.AccountCreatedEvent;
import com.software.account.event.AccountDeactivatedEvent;
import com.software.account.query.FindAccountByUsernameQuery;
import com.software.account.query.RegisteredAccount;

public interface IAccountEventHandler {
    void on(AccountCreatedEvent event);
    void on(AccountActivatedEvent event);
    void on(AccountDeactivatedEvent event);
    RegisteredAccount on(FindAccountByUsernameQuery query);
}
