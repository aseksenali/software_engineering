package com.software.account.service.interfaces;

import com.software.account.query.RegisteredAccount;

public interface IAccountService {
    void save(RegisteredAccount account);
    RegisteredAccount findByUsername(String username);
    void activateAccount(RegisteredAccount account);
    void activateAccount(String id);
    void deactivateAccount(RegisteredAccount account);
    void deactivateAccount(String id);
}
