package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.model.Account;
import ru.example.model.Client;
import ru.example.model.enums.AccountStatus;
import ru.example.repository.AccountRepository;
import ru.example.service.AccountService;
import ru.example.service.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientService clientService;

    @Override
    public List<Account> findAllByClientId(int clientId) {
        return accountRepository.findAccountsByClient_Id(clientId);
    }

    @Override
    public Optional<Account> findAccountById(int accountId) {
        return accountRepository.findById(accountId);
    }

    @Transactional
    @Override
    public int saveAccount(int clientId) {
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setStatus(AccountStatus.OPEN);

        Client client = clientService.findClientById(clientId).get();
        client.addAccount(account);

        return account.getId();
    }

    @Transactional
    @Override
    public boolean deleteAccount(int accountId, int clientId) {
        if (accountRepository.existsById(accountId)) {
            Account account = accountRepository.findById(accountId).get();
//            if (account.getBalance() != 0) {
//
//                //todo Ошибка, сначала снимите все деньги со счёта
//            }

            /*
             * Банковский счёт продолжает существовать в базе данных и имеет статус "закрыт" для того,
             * чтобы продолжать иметь доступ ко всем операциям, которые выполнялись на счёте, а также
             * видеть кто был клиентом. В списке банковских счетов клиента этот счёт уже не будет отображаться
             */

            account.setStatus(AccountStatus.CLOSED);
            Client client = clientService.findClientById(clientId).get();
            client.removeAccount(account);

            accountRepository.save(account);
            clientService.saveClient(client);
            return true;
        }
        return false;
    }

    /** Генерация номера банковского счёта при его создании */
    private String generateAccountNumber() {
        // Код валюты (рубль) для нумерации всех банковских счетов
        final String RUB_CURRENCY_CODE = "810";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            if (i == 5) {
                sb.append(RUB_CURRENCY_CODE);
                i = 8;
            }

            sb.append(ThreadLocalRandom.current().nextInt(0, 10));
        }

        //todo Сделать проверку на уникальность номера банковского счёта

        return sb.toString();
    }
}