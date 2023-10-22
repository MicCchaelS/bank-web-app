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
    public Action creatingAccountAction() {
        return createNewAction(OperationType.ACCOUNT_CREATION, null, BigDecimal.ZERO, null,
                null);
    }

    @Transactional
    @Override
    public Action replenishmentAccountAction(BigDecimal replenishmentAmount, BigDecimal reminder) {
        return createNewAction(OperationType.REPLENISHMENT, replenishmentAmount, reminder,
                null, null);
    }

    @Transactional
    @Override
    public Action withdrawalAccountAction(BigDecimal withdrawalAmount, BigDecimal reminder) {
        return createNewAction(OperationType.WITHDRAWAL, withdrawalAmount, reminder,
                null, null);
    }

    @Transactional
    @Override
    public Action transferAccountAction(OperationType operationType, BigDecimal transferAmount, BigDecimal reminder,
                                        Long senderAccountId, Long receiverAccountId) {
        return createNewAction(operationType, transferAmount, reminder, senderAccountId,
                receiverAccountId);
    }

    @Transactional
    @Override
    public Action closingAccountAction(BigDecimal balance) {
        return createNewAction(OperationType.ACCOUNT_CLOSING, null, balance, null,
                null);
    }

    private Action createNewAction(OperationType operationType, BigDecimal amount, BigDecimal reminder,
                                   Long senderAccountId, Long receiverAccountId) {
        var action = new Action();
        action.setOperationType(operationType);
        action.setAmount(amount);
        action.setReminder(reminder);
        action.setSenderAccountId(senderAccountId);
        action.setReceiverAccountId(receiverAccountId);
        action.setOperationDateTime(LocalDateTime.now());

        return action;
    }
}