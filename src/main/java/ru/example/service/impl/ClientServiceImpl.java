package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dto.client.ClientDTO;
import ru.example.dto.client.ClientsDTO;
import ru.example.exception.DeleteClientException;
import ru.example.exception.ResourceNotFoundException;
import ru.example.model.Client;
import ru.example.model.enums.AccountStatus;
import ru.example.repository.ClientRepository;
import ru.example.service.ClientService;
import ru.example.util.ModelMapperUtil;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ModelMapperUtil modelMapperUtil;

    @Override
    public List<ClientsDTO> findAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(client -> modelMapperUtil.map(client, ClientsDTO.class))
                .toList();
    }

    @Override
    public ClientDTO findClientById(int id) {
        return clientRepository.findById(id)
                .map(client -> modelMapperUtil.map(client, ClientDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Ошибка: Клиент не найден"));
    }

    @Transactional
    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        return Optional.of(clientDTO)
                .map(dto -> modelMapperUtil.map(dto, Client.class))
                .map(clientRepository::save)
                .map(client -> modelMapperUtil.map(client, ClientDTO.class))
                .orElseThrow();
    }

    @Transactional
    @Override
    public void updateClient(ClientDTO clientDTO) {
        Optional.of(clientDTO)
                .map(dto -> modelMapperUtil.map(dto, Client.class))
                .map(clientRepository::save)
                .orElseThrow();
    }

    @Transactional
    @Override
    public void deleteClient(int id) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ошибка: Клиент не найден"));

        var openAccountExists = client.getAccounts()
                .stream()
                .anyMatch(account -> account.getStatus() == AccountStatus.OPEN);

        if (openAccountExists) {
            throw new DeleteClientException("Ошибка удаления клиента. " +
                    "Сначала требуется закрыть все открытые банковские счета клиента", id);
        }

        clientRepository.delete(client);
    }
}