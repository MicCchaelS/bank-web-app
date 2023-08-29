package ru.example.service;

import ru.example.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    public List<Account> findAll();
    public Optional<Account> findByClientId(int id);
    public Account create();
    public boolean delete(int id);
}