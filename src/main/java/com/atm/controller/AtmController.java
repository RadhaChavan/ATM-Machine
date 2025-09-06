package com.atm.controller;

import com.atm.entity.*;
import com.atm.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/atm")
@Tag(name = "ATM Operations", description = "User authentication, balance inquiry, and withdrawal")
public class AtmController {

    @Autowired private UserService userService;
    @Autowired private AccountService accountService;

    @PostMapping("/auth")
    @Operation(summary = "Authenticate User", description = "Authenticate user using card number and PIN")
    public ResponseEntity<String> authenticate(
            @RequestParam String card,
            @RequestParam String pin) {
        User user = userService.authenticate(card, pin);
        return ResponseEntity.ok("Authenticated as: " + user.getName());
    }

    @GetMapping("/balance")
    @Operation(summary = "Check Account Balance")
    public ResponseEntity<BigDecimal> balance(
            @RequestParam String card,
            @RequestParam String pin,
            @RequestParam AccountType type) {
        User user = userService.authenticate(card, pin);
        return ResponseEntity.ok(accountService.getBalance(user, type));
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw Funds", description = "Withdraw specified amount from user account")
    public ResponseEntity<BigDecimal> withdraw(
            @RequestParam String card,
            @RequestParam String pin,
            @RequestParam AccountType type,
            @RequestParam BigDecimal amount) {
        User user = userService.authenticate(card, pin);
        return ResponseEntity.ok(accountService.withdraw(user, type, amount));
    }
}
