package ru.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.model.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findAccountsByClient_Id(int clientId);
    Optional<Account> findAccountByAccountNumber(String accountNumber);
}