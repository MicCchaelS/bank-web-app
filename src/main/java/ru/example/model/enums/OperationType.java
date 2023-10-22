package ru.example.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OperationType {
    ACCOUNT_CREATION("Создание счёта"),
    REPLENISHMENT("Пополнение"),
    WITHDRAWAL("Списание"),
    TRANSFER_SENDING("Совершение перевода"),
    TRANSFER_RECEIVING("Получение перевода"),
    ACCOUNT_CLOSING("Закрытие счёта");

    private final String operationName;
}