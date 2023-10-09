package ru.example.service;

import ru.example.dto.account.AccountDTO;
import ru.example.dto.account.AccountsDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    List<AccountsDTO> findAllByClientId(int clientId);
    AccountDTO findAccountById(int accountId);
    AccountDTO saveAccount(int clientId);
    void topUpAccountBalance(int accountId, BigDecimal amount);
    void withdrawMoneyFromAccount(int accountId, BigDecimal amount);
    void closeAccount(int accountId);
}