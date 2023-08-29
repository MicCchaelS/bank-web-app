package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.model.Account;
import ru.example.repository.AccountRepository;
import ru.example.service.AccountService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public Optional<Account> findByClientId(int id) {
        return Optional.empty();
    }

    @Transactional
    @Override
    public Account create() {
        return null;
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return false;
    }
}