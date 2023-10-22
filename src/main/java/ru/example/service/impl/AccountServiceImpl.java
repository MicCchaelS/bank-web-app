package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dto.account.AccountDTO;
import ru.example.exception.ResourceNotFoundException;
import ru.example.exception.account.AccountClosingException;
import ru.example.exception.account.ClosedAccountException;
import ru.example.exception.account.MoneyTransferException;
import ru.example.exception.account.TooManyOpenAccountsException;
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
    public List<AccountDTO> findAllByClientId(long clientId) {
        return accountRepository.findAllByClientId(clientId)
                .stream()
                .map(account -> modelMapperUtil.map(account, AccountDTO.class))
                .toList();
    }

    @Override
    public <T> T findAccountById(long accountId, Class<T> dtoClass) {
        return accountRepository.findById(accountId)
                .map(account -> modelMapperUtil.map(account, dtoClass))
                .orElseThrow(() -> new ResourceNotFoundException("Ошибка: Банковский счёт не найден."));
    }

    @Transactional
    @Override
    public AccountDTO saveAccount(long clientId) {
        var client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ошибка: Клиент не найден."));

        if (client.getAccounts().size() >= 10) {
            throw new TooManyOpenAccountsException("Ошибка создания банковского счёта:" +
                    " клиент не может иметь больше 10 открытых счетов.");
        }

        var account = createNewAccount();

        var action = actionService.creatingAccountAction();
        account.addAction(action);

        client.addAccount(account);

        accountRepository.save(account);
        return modelMapperUtil.map(account, AccountDTO.class);
    }

    @Transactional
    @Override
    public void topUpAccountBalance(long accountId, BigDecimal replenishmentAmount) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Ошибка: Банковский счёт не найден."));

        var newBalance = account.getBalance().add(replenishmentAmount);
        account.setBalance(newBalance);

        var action = actionService.replenishmentAccountAction(replenishmentAmount, account.getBalance());

        account.addAction(action);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void withdrawMoneyFromAccount(long accountId, BigDecimal withdrawalAmount) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Ошибка: Банковский счёт не найден."));

        account.setBalance(account.getBalance().subtract(withdrawalAmount));

        var action = actionService.withdrawalAccountAction(withdrawalAmount, account.getBalance());

        account.addAction(action);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void transferMoneyToAnotherAccount(long clientId, long accountId, String receiverAccountNumber,
                                              BigDecimal transferAmount) {
        var receiverAccount = accountRepository.findByAccountNumber(receiverAccountNumber)
                .orElseThrow(() -> new MoneyTransferException("Ошибка перевода: Банковский счёт с номером " +
                        "\"" + receiverAccountNumber + "\" не существует.", clientId, accountId));

        var senderAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Ошибка: Банковский счёт не найден."));

        final var MAX_BALANCE = BigDecimal.valueOf(1_000_000_000);
        var receiverAccountNewBalance = receiverAccount.getBalance().add(transferAmount);

        if (receiverAccountNewBalance.compareTo(MAX_BALANCE) > 0) {
            throw new MoneyTransferException("Ошибка перевода: Невозможно перевести \"" + transferAmount + " ₽\" на" +
                    " счёт с номером \"" + receiverAccountNumber + "\", так как после перевода баланс получателя" +
                    " превысит максимально допустимый лимит в 1 000 000 000 ₽.", clientId, accountId);
        }

        senderAccount.setBalance(senderAccount.getBalance().subtract(transferAmount));
        receiverAccount.setBalance(receiverAccountNewBalance);

        var senderAccountAction = actionService.transferAccountAction(OperationType.TRANSFER_SENDING, transferAmount,
                senderAccount.getBalance(), (long) senderAccount.getId(), (long) receiverAccount.getId());

        var receiverAccountAction = actionService.transferAccountAction(OperationType.TRANSFER_RECEIVING, transferAmount,
                receiverAccount.getBalance(), (long) senderAccount.getId(), (long) receiverAccount.getId());

        senderAccount.addAction(senderAccountAction);
        receiverAccount.addAction(receiverAccountAction);
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
    }

    @Transactional
    @Override
    public void closeAccount(long clientId, long accountId) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Ошибка: Банковский счёт не найден."));

        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new ClosedAccountException("Ошибка закрытия счёта. Этот счёт уже закрыт.");
        }

        if (account.getBalance().compareTo(BigDecimal.ONE) > 0) {
            throw new AccountClosingException("Ошибка закрытия счёта: Перед закрытием счёта " +
                    "клиенту необходимо снять с него все деньги.", clientId, accountId);
        }

        var action = actionService.closingAccountAction(account.getBalance());
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
        } while (accountRepository.existsByAccountNumber(accountNumber.toString()));

        return accountNumber.toString();
    }
}