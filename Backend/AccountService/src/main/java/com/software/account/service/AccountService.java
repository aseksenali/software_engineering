package com.software.account.service;

import com.software.account.query.RegisteredAccount;
import com.software.account.repository.RegisteredAccountRepository;
import com.software.account.service.interfaces.IAccountService;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@Service
public class AccountService implements IAccountService {
    private final RegisteredAccountRepository registeredAccountRepository;

    public AccountService(RegisteredAccountRepository registeredAccountRepository) {
        this.registeredAccountRepository = registeredAccountRepository;
    }

    public void save(RegisteredAccount account) {
        if (!registeredAccountRepository.findByUsername(account.getUsername()).isPresent())
            registeredAccountRepository.save(account);
    }

    private RegisteredAccount findById(String id) {
        try {
            return registeredAccountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RegisteredAccount findByUsername(String username) {
        try {
            return registeredAccountRepository.findByUsername(username).orElseThrow(AccountNotFoundException::new);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void activateAccount(RegisteredAccount account) {
        account.activate();
        registeredAccountRepository.save(account);
    }

    public void activateAccount(String id) {
        RegisteredAccount account = this.findById(id);
        assert account != null;
        this.activateAccount(account);
    }

    public void deactivateAccount(RegisteredAccount account) {
        account.deactivate();
        registeredAccountRepository.save(account);
    }

    public void deactivateAccount(String id) {
        RegisteredAccount account = this.findById(id);
        assert account != null;
        this.deactivateAccount(account);
    }
}
