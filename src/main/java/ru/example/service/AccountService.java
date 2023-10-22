package ru.example.service;

import ru.example.dto.account.AccountDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<AccountDTO> findAllByClientId(long clientId);
    <T> T findAccountById(long accountId, Class<T> dtoClass);
    AccountDTO saveAccount(long clientId);
    void topUpAccountBalance(long accountId, BigDecimal amount);
    void withdrawMoneyFromAccount(long accountId, BigDecimal amount);
    void transferMoneyToAnotherAccount(long clientId, long accountId, String receiverAccountNumber, BigDecimal amount);
    void closeAccount(long clientId, long accountId);
}