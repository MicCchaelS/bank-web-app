package ru.example.service;

import ru.example.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    public List<Account> findAllByClientId(int clientId);
    public Optional<Account> findAccountById(int accountId);
    public int saveAccount(int clientId);
    public boolean deleteAccount(int accountId, int clientId);
}