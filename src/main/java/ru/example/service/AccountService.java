package ru.example.service;

import ru.example.dto.account.AccountDTO;
import ru.example.dto.account.AccountsDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    public List<AccountsDTO> findAllByClientId(int clientId);
    public Optional<AccountDTO> findAccountById(int accountId);
    public int saveAccount(int clientId);
    public void topUpAccountBalance(int accountId, BigDecimal amount);
    public void withdrawMoneyFromAccountBalance(int accountId, BigDecimal amount);
    public boolean closeAccount(int accountId);
}