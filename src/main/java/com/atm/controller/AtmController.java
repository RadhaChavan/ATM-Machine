package com.atm.controller;

import com.atm.entity.*;
import com.atm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/atm")
public class AtmController {

    @Autowired private UserService userService;
    @Autowired private AccountService accountService;

    @PostMapping("/auth")
    public ResponseEntity<String> authenticate(
            @RequestParam String card,
            @RequestParam String pin) {
        User user = userService.authenticate(card, pin);
        return ResponseEntity.ok("Authenticated as: " + user.getName());
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> balance(
            @RequestParam String card,
            @RequestParam String pin,
            @RequestParam AccountType type) {
        User user = userService.authenticate(card, pin);
        return ResponseEntity.ok(accountService.getBalance(user, type));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<BigDecimal> withdraw(
            @RequestParam String card,
            @RequestParam String pin,
            @RequestParam AccountType type,
            @RequestParam BigDecimal amount) {
        User user = userService.authenticate(card, pin);
        return ResponseEntity.ok(accountService.withdraw(user, type, amount));
    }
}
