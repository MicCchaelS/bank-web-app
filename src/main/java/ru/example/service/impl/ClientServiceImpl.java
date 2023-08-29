package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.model.Client;
import ru.example.repository.ClientRepository;
import ru.example.service.ClientService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public Optional<Client> findById(int id) {
        return Optional.empty();
    }

    @Transactional
    @Override
    public Client create() {
        return null;
    }

    @Transactional
    @Override
    public Optional<Client> update(int id) {
        return Optional.empty();
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return false;
    }
}
