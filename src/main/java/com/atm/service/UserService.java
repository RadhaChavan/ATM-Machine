package com.atm.service;

import com.atm.entity.User;
import com.atm.exception.AuthenticationException;
import com.atm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final int MAX_ATTEMPTS = 3;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticate(String cardNumber, String pin) {
        Optional<User> optionalUser = userRepo.findByCardNumber(cardNumber);

        if (!optionalUser.isPresent()) {
            throw new AuthenticationException("Card number not found.");
        }

        User user = optionalUser.get();

        if (user.isBlocked()) {
            throw new AuthenticationException("Account is blocked.");
        }

        boolean pinMatched = passwordEncoder.matches(pin, user.getPin());

        if (!pinMatched) {
            int attempts = user.getLoginAttempts() + 1;
            user.setLoginAttempts(attempts);

            if (attempts >= MAX_ATTEMPTS) {
                user.setBlocked(true);
            }

            userRepo.save(user);
            throw new AuthenticationException("Invalid PIN. Attempt " + attempts + "/" + MAX_ATTEMPTS);
        }

        // Reset login attempts on success
        user.setLoginAttempts(0);
        userRepo.save(user);

        return user;
    }
}
