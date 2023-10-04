package ru.example.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class DeleteClientException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final int clientId;

    public DeleteClientException(String message, int clientId) {
        super(message);
        this.clientId = clientId;
    }
}