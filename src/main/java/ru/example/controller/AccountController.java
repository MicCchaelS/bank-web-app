package ru.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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

    @PatchMapping("/{accountId}")
    public String closeAccount(@PathVariable("accountId") int accountId, @PathVariable("clientId") int clientId) {
        if (!accountService.closeAccount(accountId, clientId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/api/clients/{clientId}/accounts";
    }

    @PatchMapping("/top-up-balance")
    public String topUpBalance() {
        //todo
        return "redirect:/api/clients/{clientId}/accounts/"; // + "id"
    }

    @PatchMapping("/withdraw-money")
    public String withdrawMoney() {
        //todo
        return "redirect:/api/clients/{clientId}/accounts/"; // + "id"
    }
}