package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dto.account.AccountDTO;
import ru.example.dto.account.AccountsDTO;
import ru.example.exception.CloseAccountException;
import ru.example.exception.ClosedAccountException;
import ru.example.exception.ResourceNotFoundException;
import ru.example.model.Account;
import ru.example.model.enums.AccountStatus;
import ru.example.model.enums.OperationType;
import ru.example.repository.AccountRepository;
import ru.example.repository.ClientRepository;
import ru.example.service.AccountService;
import ru.example.service.ActionService;
import ru.example.util.ModelMapperUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    private final ActionService actionService;

    private final ModelMapperUtil modelMapperUtil;

    @Override
    public List<AccountsDTO> findAllByClientId(int clientId) {
        return accountRepository.findAccountsByClient_Id(clientId)
                .stream()
                .map(account -> modelMapperUtil.map(account, AccountsDTO.class))
                .toList();
    }

    @Override
    public AccountDTO findAccountById(int accountId) {
        return accountRepository.findById(accountId)
                .map(account -> modelMapperUtil.map(account, AccountDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Банковский счёт не найден"));
    }

    @Transactional
    @Override
    public AccountDTO saveAccount(int clientId) {
        var client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Клиент не найден"));

        var account = createNewAccount();

        var action = actionService.createNewAction(OperationType.ACCOUNT_CREATION, null, BigDecimal.ZERO);
        account.addAction(action);

        client.addAccount(account);

        accountRepository.save(account);
        return modelMapperUtil.map(account, AccountDTO.class);
    }

    @Transactional
    @Override
    public void topUpAccountBalance(int accountId, BigDecimal amount) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Банковский счёт не найден"));

        var newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);

        var action = actionService.createNewAction(OperationType.REPLENISHMENT, amount, account.getBalance());

        account.addAction(action);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void withdrawMoneyFromAccount(int accountId, BigDecimal amount) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Банковский счёт не найден"));

        account.setBalance(account.getBalance().subtract(amount));

        var action = actionService.createNewAction(OperationType.WITHDRAWAL, amount, account.getBalance());

        account.addAction(action);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void closeAccount(int accountId) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Банковский счёт не найден"));

        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new ClosedAccountException("Ошибка закрытия счёта. Этот счёт уже закрыт");
        }

        if (account.getBalance().compareTo(BigDecimal.ONE) > 0) {
            throw new CloseAccountException("Ошибка закрытия счёта. Перед закрытием счёта " +
                    "требуется снять все деньги с него", account.getClient().getId(), accountId);
        }

        var action = actionService.createNewAction(OperationType.ACCOUNT_REMOVAL,
                null, account.getBalance());
        account.addAction(action);

        account.setStatus(AccountStatus.CLOSED);
        accountRepository.save(account);
    }

    private Account createNewAccount() {
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setStatus(AccountStatus.OPEN);
        account.setBalance(BigDecimal.ZERO);
        return accountRepository.save(account);
    }

    /** Генерация номера банковского счёта при его создании */
    private String generateAccountNumber() {
        // Код валюты (рубль) для нумерации всех банковских счетов в этой валюте
        final String RUB_CURRENCY_CODE = "810";
        final int ACCOUNT_NUMBER_LENGTH = 20;

        StringBuilder accountNumber;

        do {
            accountNumber = new StringBuilder();
            for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
                if (i == 5) {
                    accountNumber.append(RUB_CURRENCY_CODE);
                    i = 8;
                }

                accountNumber.append(ThreadLocalRandom.current().nextInt(0, 10));
            }
        } while (accountRepository.existsAccountByAccountNumber(accountNumber.toString()));

        return accountNumber.toString();
    }
}