package ru.example.service;

import ru.example.model.Action;
import ru.example.model.enums.OperationType;

import java.math.BigDecimal;

public interface ActionService {

    Action creatingAccountAction();
    Action replenishmentAccountAction(BigDecimal replenishmentAmount, BigDecimal reminder);
    Action withdrawalAccountAction(BigDecimal withdrawalAmount, BigDecimal reminder);
    Action transferAccountAction(OperationType operationType, BigDecimal transferAmount, BigDecimal reminder,
                                 Long senderAccountId, Long receiverAccountId);
    Action closingAccountAction(BigDecimal balance);
}