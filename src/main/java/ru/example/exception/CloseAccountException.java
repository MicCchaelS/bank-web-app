package ru.example.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class CloseAccountException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final int clientId;
    private final int accountId;

    public CloseAccountException(String message, int clientId, int accountId) {
        super(message);
        this.clientId = clientId;
        this.accountId = accountId;
    }
}