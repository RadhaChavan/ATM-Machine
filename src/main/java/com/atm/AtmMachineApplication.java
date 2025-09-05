package com.atm;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.atm.entity.Account;
import com.atm.entity.AccountType;
import com.atm.entity.User;
import com.atm.repository.AccountRepository;
import com.atm.repository.UserRepository;

@SpringBootApplication
public class AtmMachineApplication {
	
	
	@Autowired private UserRepository userRepo;
    @Autowired private AccountRepository accountRepo;
    @Autowired private org.springframework.security.crypto.password.PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(AtmMachineApplication.class, args);
	}
	
	 @Bean
	    public CommandLineRunner initData() {
	        return args -> {
	            if (userRepo.count() == 0) {
	                User user = new User();
	                user.setCardNumber("11111");
	                user.setPin(encoder.encode("1234"));
	                user.setName("Alex");
	                user.setBlocked(false);
	                user.setLoginAttempts(0);
	                userRepo.save(user);

	                Account acc = new Account();
	                acc.setUser(user);
	                acc.setAccountType(AccountType.SAVINGS);
	                acc.setBalance(new BigDecimal("500.00"));
	                accountRepo.save(acc);
	            }
	        };
	    }

}
