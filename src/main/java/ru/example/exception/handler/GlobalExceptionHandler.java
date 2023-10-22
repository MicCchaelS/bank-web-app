package ru.example.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.example.exception.*;
import ru.example.exception.account.AccountClosingException;
import ru.example.exception.account.ClosedAccountException;
import ru.example.exception.account.MoneyTransferException;
import ru.example.exception.account.TooManyOpenAccountsException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleException(ResourceNotFoundException e, Model model) {
        log.error(e.getMessage(), e);
        model.addAttribute("exception", e.getMessage());
        return "exception/exception";
    }

    @ExceptionHandler(ClientDeletionException.class)
    public String handleException(ClientDeletionException e, RedirectAttributes redirectAttributes) {
        log.error(e.getMessage(), e);
        redirectAttributes.addFlashAttribute("clientDeletionException", e.getMessage());
        return "redirect:/api/clients/" + e.getClientId();
    }

    @ExceptionHandler(TooManyOpenAccountsException.class)
    public String handleException(TooManyOpenAccountsException e, Model model) {
        log.error(e.getMessage(), e);
        model.addAttribute("exception", e.getMessage());
        return "exception/exception";
    }

    @ExceptionHandler(MoneyTransferException.class)
    public String handleException(MoneyTransferException e, RedirectAttributes redirectAttributes) {
        log.error(e.getMessage(), e);
        redirectAttributes.addFlashAttribute("moneyTransferException", e.getMessage());
        return "redirect:/api/clients/" + e.getClientId() + "/accounts/" + e.getAccountId() + "/transfer";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ClosedAccountException.class)
    public ResponseStatusException handleException(ClosedAccountException e) {
        log.error(e.getMessage(), e);
        return new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccountClosingException.class)
    public String handleException(AccountClosingException e, RedirectAttributes redirectAttributes) {
        log.error(e.getMessage(), e);
        redirectAttributes.addFlashAttribute("accountClosingException", e.getMessage());
        return "redirect:/api/clients/" + e.getClientId() + "/accounts/" + e.getAccountId();
    }
}