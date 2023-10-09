package ru.example.service;

import ru.example.model.Action;
import ru.example.model.enums.OperationType;

import java.math.BigDecimal;

public interface ActionService {

    Action createNewAction(OperationType operationType, BigDecimal amount, BigDecimal reminder);
}