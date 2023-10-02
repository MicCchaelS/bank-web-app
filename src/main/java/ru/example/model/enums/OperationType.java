package ru.example.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OperationType {
    REPLENISHMENT("Пополнение"),
    WITHDRAWAL("Списание"),
    ACCOUNT_CREATION("Создание счёта"),
    ACCOUNT_REMOVAL("Закрытие счёта");

    private final String operationName;
}