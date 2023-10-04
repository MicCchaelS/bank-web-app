package ru.example.service;

import ru.example.dto.account.AccountDTO;
import ru.example.dto.account.AccountsDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    public List<AccountsDTO> findAllByClientId(int clientId);
    public AccountDTO findAccountById(int accountId);
    public AccountDTO saveAccount(int clientId);
    public void topUpAccountBalance(int accountId, BigDecimal amount);
    public void withdrawMoneyFromAccount(int accountId, BigDecimal amount);
    public void closeAccount(int accountId);
}