package ru.example.exception.account;

import lombok.Getter;

import java.io.Serial;

@Getter
public class MoneyTransferException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final long clientId;
    private final long accountId;

    public MoneyTransferException(String message, long clientId, long accountId) {
        super(message);
        this.clientId = clientId;
        this.accountId = accountId;
    }
}