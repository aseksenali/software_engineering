package com.software.account.handler;

import com.software.account.event.AccountActivatedEvent;
import com.software.account.event.AccountCreatedEvent;
import com.software.account.event.AccountDeactivatedEvent;
import com.software.account.handler.interfaces.IAccountEventHandler;
import com.software.account.query.FindAccountByUsernameQuery;
import com.software.account.query.RegisteredAccount;
import com.software.account.service.interfaces.IAccountService;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class AccountEventHandler implements IAccountEventHandler {
    private final IAccountService accountService;

    public AccountEventHandler(IAccountService accountService) {
        this.accountService = accountService;
    }

    @EventHandler
    public void on(AccountCreatedEvent event) {
        accountService.save(new RegisteredAccount(event.getId(), event.getUsername()));
    }

    @EventHandler
    public void on(AccountActivatedEvent event) {
        this.accountService.activateAccount(event.getId());
    }

    @EventHandler
    public void on(AccountDeactivatedEvent event) {
        this.accountService.deactivateAccount(event.getId());
    }

    @QueryHandler
    public RegisteredAccount on(FindAccountByUsernameQuery query) {
        return this.accountService.findByUsername(query.getUsername());
    }
}
