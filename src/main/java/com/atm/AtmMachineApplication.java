package com.atm;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication 
@OpenAPIDefinition(
	    info = @Info(
	        title = "ATM API Documentation",
	        version = "v1",
	        description = "This API allows users to manage marriage registrations and related services. Please use the JWT token for authorization."
	    )
	)
	@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", description = "Enter 'Bearer ' [space] and then your token in the input below. Example: 'Bearer my-token'")

public class AtmMachineApplication {

	@Autowired private UserRepository userRepo;
    @Autowired private AccountRepository accountRepo;
    @Autowired private org.springframework.security.crypto.password.PasswordEncoder encoder;


	private static final Logger logger = LoggerFactory.getLogger(AtmMachineApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AtmMachineApplication.class, args);
        logger.info("ATM Appliation is Started  .......................");
    }
    
    @Bean
    public CommandLineRunner initData() {
        return args -> {
            System.out.println("==== FORCING DATA INSERTION ====");

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
        };
    }
}
    

