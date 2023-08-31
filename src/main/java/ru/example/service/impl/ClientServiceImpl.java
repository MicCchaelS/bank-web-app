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
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findClientById(int id) {
        return clientRepository.findById(id);
    }

    @Transactional
    @Override
    public int saveClient(Client client) {
        return clientRepository.save(client).getId();
    }

    @Transactional
    @Override
    public void updateClient(Client client) {
        clientRepository.save(client);
    }

    @Transactional
    @Override
    public boolean deleteClient(int id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
