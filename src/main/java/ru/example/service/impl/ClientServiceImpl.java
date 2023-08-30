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
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(int id) {
        return clientRepository.findById(id);
    }

    @Transactional
    @Override
    public int create(Client client) {
        return clientRepository.save(client).getId();
    }

    @Transactional
    @Override
    public void update(Client client) {
        clientRepository.save(client);
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        if (clientRepository.findById(id).isPresent()) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
