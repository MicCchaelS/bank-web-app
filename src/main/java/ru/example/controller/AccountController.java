package ru.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.example.dto.account.AccountDTO;
import ru.example.dto.account.AccountTopUpDTO;
import ru.example.dto.account.AccountWithdrawDTO;
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
        return accountService.findAccountById(accountId, AccountDTO.class)
                .map(accountDTO -> {
                    model.addAttribute("account", accountDTO);
                    model.addAttribute("clientId", clientId);
                    return "account/account";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String createAccount(@PathVariable("clientId") int clientId) {
        return "redirect:/api/clients/{clientId}/accounts/" + accountService.saveAccount(clientId);
    }

    @GetMapping("/{accountId}/top-up")
    public String showTopUpAccountForm(@PathVariable("clientId") int clientId,
                                       @PathVariable("accountId") int accountId,
                                       Model model) {
        return accountService.findAccountById(accountId, AccountTopUpDTO.class)
                .map(accountTopUpDTO -> {
                    model.addAttribute("account", accountTopUpDTO);
                    model.addAttribute("clientId", clientId);
                    return "account/topUpAccount";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{accountId}/top-up")
    public String topUpBalance(@PathVariable("clientId") int clientId,
                               @PathVariable("accountId") int accountId,
                               @ModelAttribute("account") @Valid AccountTopUpDTO accountTopUpDTO,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", clientId);
            return "account/topUpAccount";
        }

        accountService.topUpAccountBalance(accountId, accountTopUpDTO.getReplenishmentAmount());
        return "redirect:/api/clients/{clientId}/accounts/{accountId}";
    }

    @GetMapping("/{accountId}/withdraw")
    public String showWithdrawAccountForm(@PathVariable("clientId") int clientId,
                                          @PathVariable("accountId") int accountId,
                                          Model model) {
        return accountService.findAccountById(accountId, AccountWithdrawDTO.class)
                .map(accountWithdrawDTO -> {
                    model.addAttribute("account", accountWithdrawDTO);
                    model.addAttribute("clientId", clientId);
                    return "account/withdrawAccount";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{accountId}/withdraw")
    public String withdrawMoney(@PathVariable("clientId") int clientId,
                                @PathVariable("accountId") int accountId,
                                @ModelAttribute("account") @Valid AccountWithdrawDTO accountWithdrawDTO,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", clientId);
            return "account/withdrawAccount";
        }

        accountService.withdrawMoneyFromAccount(accountId, accountWithdrawDTO.getWithdrawalAmount());
        return "redirect:/api/clients/{clientId}/accounts/{accountId}";
    }

    @PatchMapping("/{accountId}/close")
    public String closeAccount(@PathVariable("accountId") int accountId) {
        if (!accountService.closeAccount(accountId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/api/clients/{clientId}/accounts";
    }
}