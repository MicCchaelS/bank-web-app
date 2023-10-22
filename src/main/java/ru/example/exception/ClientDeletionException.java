package ru.example.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ClientDeletionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final long clientId;

    public ClientDeletionException(String message, long clientId) {
        super(message);
        this.clientId = clientId;
    }
}