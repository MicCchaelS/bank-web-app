package ru.example.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.example.exception.CloseAccountException;
import ru.example.exception.ClosedAccountException;
import ru.example.exception.DeleteClientException;
import ru.example.exception.ResourceNotFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleException(ResourceNotFoundException e, Model model) {
        log.error(e.getMessage(), e);
        model.addAttribute("error", e.getMessage());
        return "error/error";
    }

    @ExceptionHandler(DeleteClientException.class)
    public String handleException(DeleteClientException e, RedirectAttributes redirectAttributes) {
        log.error(e.getMessage(), e);
        redirectAttributes.addFlashAttribute("deleteClientError", e.getMessage());
        return "redirect:/api/clients/" + e.getClientId();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ClosedAccountException.class)
    public ResponseStatusException handleException(ClosedAccountException e) {
        log.error(e.getMessage(), e);
        return new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CloseAccountException.class)
    public String handleException(CloseAccountException e, RedirectAttributes redirectAttributes) {
        log.error(e.getMessage(), e);
        redirectAttributes.addFlashAttribute("closeAccountError", e.getMessage());
        return "redirect:/api/clients/" + e.getClientId() + "/accounts/" + e.getAccountId();
    }
}