package com.atm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atm.entity.Account;
import com.atm.entity.AccountType;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserIdAndAccountType(Long userId, AccountType accountType);
}
