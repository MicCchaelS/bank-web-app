package ru.example.service;

import ru.example.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    public List<Account> findAllByClientId(int clientId);
    public Optional<Account> findAccountById(int accountId);
    public int saveAccount(int clientId);
    public void topUpAccountBalance(int accountId, BigDecimal amount);
    public void withdrawMoneyFromAccountBalance(int accountId, BigDecimal amount);
    public void closeAccount(int accountId);
}