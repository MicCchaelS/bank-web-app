package ru.example.exception.account;

import java.io.Serial;

public class TooManyOpenAccountsException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TooManyOpenAccountsException(String message) {
        super(message);
    }
}