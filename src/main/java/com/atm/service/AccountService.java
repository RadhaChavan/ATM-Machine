package com.atm.service;

import com.atm.entity.*;
import com.atm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private TransactionRepository txnRepo;

    public BigDecimal getBalance(User user, AccountType type) {
        Optional<Account> optionalAccount = accountRepo.findByUserIdAndAccountType(user.getId(), type);

        if (!optionalAccount.isPresent()) {
            throw new RuntimeException("Account not found.");
        }

        Account acc = optionalAccount.get();
        return acc.getBalance();
    }

    @Transactional
    public BigDecimal withdraw(User user, AccountType type, BigDecimal amount) {
        Optional<Account> optionalAccount = accountRepo.findByUserIdAndAccountType(user.getId(), type);

        if (!optionalAccount.isPresent()) {
            throw new RuntimeException("Account not found.");
        }

        Account acc = optionalAccount.get();
        BigDecimal currentBalance = acc.getBalance();

        if (currentBalance.compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds.");
        }

        // Deduct amount
        acc.setBalance(currentBalance.subtract(amount));
        accountRepo.save(acc);

        // Record transaction
        Transaction txn = new Transaction();
        txn.setAccount(acc);
        txn.setType(TransactionType.WITHDRAW);
        txn.setAmount(amount);
        txn.setDetails("Withdrawal of " + amount);
        txnRepo.save(txn);

        return acc.getBalance();
    }
}

