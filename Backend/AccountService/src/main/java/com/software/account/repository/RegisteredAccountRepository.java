package com.software.account.repository;

import com.software.account.query.RegisteredAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisteredAccountRepository extends JpaRepository<RegisteredAccount, String> {
    Optional<RegisteredAccount> findByUsername(String username);
}
