package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.model.Action;
import ru.example.model.enums.OperationType;
import ru.example.repository.ActionRepository;
import ru.example.service.ActionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;

    @Transactional
    @Override
    public Action createNewAction(OperationType operationType, BigDecimal amount, BigDecimal reminder) {
        var action = new Action();
        action.setOperationType(operationType);
        action.setAmount(amount);
        action.setReminder(reminder);
        action.setOperationDateTime(LocalDateTime.now());
        return action;
    }
}