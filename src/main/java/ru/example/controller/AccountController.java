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
        return "account/accounts";
    }

    @GetMapping("/{accountId}")
    public String findAccountById(@PathVariable("accountId") int accountId, Model model) {
        model.addAttribute("account", accountService.findAccountById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return "account/account";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createAccount(@PathVariable("clientId") int clientId) {
        return "redirect:/api/clients/{clientId}/accounts/" + accountService.saveAccount(clientId);
    }

    @DeleteMapping("/{accountId}")
    public String deleteAccount(@PathVariable("accountId") int accountId, @PathVariable("clientId") int clientId) {
        if (!accountService.deleteAccount(accountId, clientId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/api/clients/{clientId}/accounts";
    }

//    @PatchMapping("/top-up-balance")
//    public String topUpBalance() {
//
//        return "redirect:/api/clients/{clientId}/accounts/" //+ "id";
//    }
//
//    @PatchMapping("/withdraw-money")
//    public String withdrawMoney() {
//
//        return "redirect:/api/clients/{clientId}/accounts/" //+ "id";
//    }
}