package ru.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.account.AccountDTO;
import ru.example.model.enums.OperationType;
import ru.example.service.AccountService;
import ru.example.validation.validator.AccountDTOValidator;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/clients/{clientId}/accounts")
public class AccountController {

    private final AccountService accountService;

    private final AccountDTOValidator accountDTOValidator;

    @GetMapping
    public String findAllAccounts(@PathVariable("clientId") int clientId, Model model) {
        model.addAttribute("accounts", accountService.findAllByClientId(clientId));
        model.addAttribute("clientId", clientId);
        return "account/accounts";
    }

    @GetMapping("/{accountId}")
    public String findAccountById(Model model, @PathVariable("accountId") int accountId,
                                  @PathVariable("clientId") int clientId) {
        model.addAttribute("account", accountService.findAccountById(accountId));
        model.addAttribute("clientId", clientId);
        return "account/account";
    }

    @PostMapping
    public String createAccount(@PathVariable("clientId") int clientId) {
        return "redirect:/api/clients/{clientId}/accounts/" + accountService.saveAccount(clientId).getId();
    }

    @GetMapping("/{accountId}/top-up")
    public String showTopUpAccountForm(@PathVariable("clientId") int clientId,
                                       @PathVariable("accountId") int accountId,
                                       Model model) {
        model.addAttribute("account", accountService.findAccountById(accountId));
        model.addAttribute("clientId", clientId);
        return "account/topUpAccount";
    }

    @PatchMapping("/{accountId}/top-up")
    public String topUpBalance(@PathVariable("clientId") int clientId,
                               @PathVariable("accountId") int accountId,
                               @ModelAttribute("account") @Valid AccountDTO accountDTO,
                               BindingResult bindingResult, Model model) {
        accountDTO.setOperationType(OperationType.REPLENISHMENT);
        accountDTOValidator.validate(accountDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", clientId);
            return "account/topUpAccount";
        }

        accountService.topUpAccountBalance(accountId, accountDTO.getAmount());
        return "redirect:/api/clients/{clientId}/accounts/{accountId}";
    }

    @GetMapping("/{accountId}/withdraw")
    public String showWithdrawAccountForm(@PathVariable("clientId") int clientId,
                                          @PathVariable("accountId") int accountId,
                                          Model model) {
        model.addAttribute("account", accountService.findAccountById(accountId));
        model.addAttribute("clientId", clientId);
        return "account/withdrawAccount";
    }

    @PatchMapping("/{accountId}/withdraw")
    public String withdrawMoney(@PathVariable("clientId") int clientId,
                                @PathVariable("accountId") int accountId,
                                @ModelAttribute("account") @Valid AccountDTO accountDTO,
                                BindingResult bindingResult, Model model) {
        accountDTO.setOperationType(OperationType.WITHDRAWAL);
        accountDTOValidator.validate(accountDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", clientId);
            return "account/withdrawAccount";
        }

        accountService.withdrawMoneyFromAccount(accountId, accountDTO.getAmount());
        return "redirect:/api/clients/{clientId}/accounts/{accountId}";
    }

    @PatchMapping("/{accountId}/close")
    public String closeAccount(@PathVariable("accountId") int accountId) {
        accountService.closeAccount(accountId);
        return "redirect:/api/clients/{clientId}/accounts";
    }
}