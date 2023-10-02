package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dto.account.AccountsDTO;
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
import java.util.Optional;
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
    public <T> Optional<T> findAccountById(int accountId, Class<T> dtoClass) {
        return accountRepository.findById(accountId)
                .map(account -> modelMapperUtil.map(account, dtoClass));
    }

    @Transactional
    @Override
    public int saveAccount(int clientId) {
        var account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setStatus(AccountStatus.OPEN);
        account.setBalance(BigDecimal.ZERO);

        var client = clientRepository.findById(clientId).get();
        client.addAccount(account);

        var action = actionService.createNewAction(OperationType.ACCOUNT_CREATION,
                null, BigDecimal.ZERO);
        account.addAction(action);

        return accountRepository.save(account).getId();
    }

    @Transactional
    @Override
    public void topUpAccountBalance(int accountId, BigDecimal amount) {
        var account = accountRepository.findById(accountId).get();

//        if (account.getStatus() == AccountStatus.CLOSED) {
//            //todo Ошибка: счёт закрыт
//        }

//        final BigDecimal MAX_BALANCE = BigDecimal.valueOf(1_000_000_000);
        BigDecimal newBalance = account.getBalance().add(amount);
//        if (newBalance.compareTo(MAX_BALANCE) > 0) {
//            //todo Ошибка: максимальная сумма, которая может храниться на балансе счёта - 1 000 000 000 рублей
//        }

        account.setBalance(newBalance);

        var action = actionService.createNewAction(OperationType.REPLENISHMENT,
                amount, account.getBalance());

        account.addAction(action);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void withdrawMoneyFromAccount(int accountId, BigDecimal amount) {
        var account = accountRepository.findById(accountId).get();
//        if (account.getStatus() == AccountStatus.CLOSED) {
//            //todo Ошибка: счёт закрыт
//        }

//        if (amount.compareTo(account.getBalance()) > 0) {
//            //todo Ошибка: на балансе недостаточно средств
//        }

        account.setBalance(account.getBalance().subtract(amount));

        var action = actionService.createNewAction(OperationType.WITHDRAWAL,
                amount, account.getBalance());

        account.addAction(action);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public boolean closeAccount(int accountId) {
        return accountRepository.findById(accountId)
                .map(account -> {
//                    if (account.getStatus() == AccountStatus.CLOSED) {
//                        //todo Ошибка: счёт уже закрыт
//                    }
//                    if (account.getBalance() != 0) {
//                        //todo Ошибка: сначала снимите все деньги со счёта
//                    }
                    var action = actionService.createNewAction(OperationType.ACCOUNT_REMOVAL,
                            null, account.getBalance());
                    account.addAction(action);

                    account.setStatus(AccountStatus.CLOSED);
                    accountRepository.save(account);
                    return true;
                })
                .orElse(false);
    }

    /** Генерация номера банковского счёта при его создании */
    private String generateAccountNumber() {
        // Код валюты (рубль) для нумерации всех банковских счетов в этой валюте
        final String RUB_CURRENCY_CODE = "810";

        StringBuilder accountNumber;

        do {
            accountNumber = new StringBuilder();
            for (int i = 0; i < 20; i++) {
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