package ru.example.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountStatus {
    OPEN("Открыт"),
    CLOSED("Закрыт");

    private final String statusName;
}