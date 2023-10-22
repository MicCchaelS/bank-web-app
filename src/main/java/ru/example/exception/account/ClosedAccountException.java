package ru.example.exception.account;

import java.io.Serial;

public class ClosedAccountException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ClosedAccountException(String message) {
        super(message);
    }
}