package ru.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.example.model.Account;
import ru.example.service.AccountService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/clients/{clientId}/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public String findAllAccounts(@PathVariable("clientId") int clientId, Model model) {
        model.addAttribute("accounts", accountService.findAllByClientId(clientId));
        model.addAttribute("clientId", clientId);
        return "account/accounts";
    }

    @GetMapping("/{accountId}")
    public String findAccountById(Model model, @PathVariable("accountId") int accountId,
                                  @PathVariable("clientId") int clientId) {
        model.addAttribute("account", accountService.findAccountById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        model.addAttribute("clientId", clientId);
        return "account/account";
    }

    @PostMapping
    public String createAccount(@PathVariable("clientId") int clientId) {
        return "redirect:/api/clients/{clientId}/accounts/" + accountService.saveAccount(clientId);
    }

    @PatchMapping("/{accountId}/top-up")
    public String topUpBalance(@PathVariable("accountId") int accountId, Account account) {
        accountService.topUpAccountBalance(accountId, account.getAmount());
        return "redirect:/api/clients/{clientId}/accounts/{accountId}";
    }

    @PatchMapping("/{accountId}/withdraw")
    public String withdrawMoney(@PathVariable("accountId") int accountId, Account account) {
        accountService.withdrawMoneyFromAccountBalance(accountId, account.getAmount());
        return "redirect:/api/clients/{clientId}/accounts/{accountId}";
    }

    @PatchMapping("/{accountId}/close")
    public String closeAccount(@PathVariable("accountId") int accountId) {
        accountService.closeAccount(accountId);
        return "redirect:/api/clients/{clientId}/accounts";
    }
}