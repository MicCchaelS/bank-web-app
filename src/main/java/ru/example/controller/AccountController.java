package ru.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.dto.account.AccountDTO;
import ru.example.dto.account.TopUpAccountDTO;
import ru.example.dto.account.TransferAccountDTO;
import ru.example.dto.account.WithdrawAccountDTO;
import ru.example.service.AccountService;
import ru.example.validation.validator.account.TopUpAccountDTOValidator;
import ru.example.validation.validator.account.TransferAccountDTOValidator;
import ru.example.validation.validator.account.WithdrawAccountDTOValidator;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/clients/{clientId}/accounts")
public class AccountController {

    private final AccountService accountService;

    private final TopUpAccountDTOValidator topUpAccountDTOValidator;
    private final WithdrawAccountDTOValidator withdrawAccountDTOValidator;
    private final TransferAccountDTOValidator transferAccountDTOValidator;

    @GetMapping
    public String findAllAccounts(@PathVariable("clientId") long clientId, Model model) {
        model.addAttribute("accounts", accountService.findAllByClientId(clientId));
        model.addAttribute("clientId", clientId);
        return "account/accounts";
    }

    @GetMapping("/{accountId}")
    public String findAccountById(@PathVariable("clientId") long clientId,
                                  @PathVariable("accountId") long accountId,
                                  Model model) {
        model.addAttribute("account", accountService.findAccountById(accountId, AccountDTO.class));
        model.addAttribute("clientId", clientId);
        return "account/account";
    }

    @PostMapping
    public String createAccount(@PathVariable("clientId") long clientId) {
        return "redirect:/api/clients/{clientId}/accounts/" + accountService.saveAccount(clientId).getId();
    }

    @GetMapping("/{accountId}/top-up")
    public String showTopUpAccountPage(@PathVariable("clientId") long clientId,
                                       @PathVariable("accountId") long accountId,
                                       Model model) {
        model.addAttribute("account", accountService.findAccountById(accountId, TopUpAccountDTO.class));
        model.addAttribute("clientId", clientId);
        return "account/topUpAccount";
    }

    @PatchMapping("/{accountId}/top-up")
    public String topUpBalance(@PathVariable("clientId") long clientId,
                               @PathVariable("accountId") long accountId,
                               @ModelAttribute("account") @Valid TopUpAccountDTO topUpAccountDTO,
                               BindingResult bindingResult,
                               Model model) {
        topUpAccountDTOValidator.validate(topUpAccountDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", clientId);
            return "account/topUpAccount";
        }

        accountService.topUpAccountBalance(accountId, topUpAccountDTO.getReplenishmentAmount());
        return "redirect:/api/clients/{clientId}/accounts/{accountId}";
    }

    @GetMapping("/{accountId}/withdraw")
    public String showWithdrawAccountPage(@PathVariable("clientId") long clientId,
                                          @PathVariable("accountId") long accountId,
                                          Model model) {
        model.addAttribute("account", accountService.findAccountById(accountId, WithdrawAccountDTO.class));
        model.addAttribute("clientId", clientId);
        return "account/withdrawAccount";
    }

    @PatchMapping("/{accountId}/withdraw")
    public String withdrawMoney(@PathVariable("clientId") long clientId,
                                @PathVariable("accountId") long accountId,
                                @ModelAttribute("account") @Valid WithdrawAccountDTO withdrawAccountDTO,
                                BindingResult bindingResult,
                                Model model) {
        withdrawAccountDTOValidator.validate(withdrawAccountDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", clientId);
            return "account/withdrawAccount";
        }

        accountService.withdrawMoneyFromAccount(accountId, withdrawAccountDTO.getWithdrawalAmount());
        return "redirect:/api/clients/{clientId}/accounts/{accountId}";
    }

    @GetMapping("/{accountId}/transfer")
    public String showTransferAccountPage(@PathVariable("clientId") long clientId,
                                          @PathVariable("accountId") long accountId,
                                          Model model) {
        model.addAttribute("account", accountService.findAccountById(accountId, TransferAccountDTO.class));
        model.addAttribute("clientId", clientId);
        return "account/transferAccount";
    }

    @PatchMapping("/{accountId}/transfer")
    public String transferMoney(@PathVariable("clientId") long clientId,
                                @PathVariable("accountId") long accountId,
                                @ModelAttribute("account") @Valid TransferAccountDTO transferAccountDTO,
                                BindingResult bindingResult,
                                Model model) {
        transferAccountDTOValidator.validate(transferAccountDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("clientId", clientId);
            return "account/transferAccount";
        }

        accountService.transferMoneyToAnotherAccount(clientId, accountId,
                transferAccountDTO.getReceiverAccountNumber(), transferAccountDTO.getTransferAmount());
        return "redirect:/api/clients/{clientId}/accounts/{accountId}";
    }

    @PatchMapping("/{accountId}/close")
    public String closeAccount(@PathVariable("clientId") long clientId,
                               @PathVariable("accountId") long accountId) {
        accountService.closeAccount(clientId, accountId);
        return "redirect:/api/clients/{clientId}/accounts";
    }
}