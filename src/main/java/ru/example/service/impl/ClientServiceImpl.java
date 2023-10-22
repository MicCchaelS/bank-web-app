package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dto.client.ClientDTO;
import ru.example.dto.client.ClientsDTO;
import ru.example.exception.ClientDeletionException;
import ru.example.exception.ResourceNotFoundException;
import ru.example.model.Client;
import ru.example.model.enums.AccountStatus;
import ru.example.repository.ClientRepository;
import ru.example.service.ClientService;
import ru.example.service.PassportService;
import ru.example.util.ModelMapperUtil;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ModelMapperUtil modelMapperUtil;

    private final PassportService passportService;

    @Override
    public List<ClientsDTO> findSpecificClientsPassportsFields() {
        return clientRepository.findClientsWithPassports();
    }

    @Override
    public ClientDTO findClientById(long id) {
        return clientRepository.findById(id)
                .map(client -> modelMapperUtil.map(client, ClientDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Ошибка: Клиент не найден."));
    }

    @Transactional
    @Override
    public Client saveClient(ClientDTO clientDTO) {
        return Optional.of(clientDTO)
                .map(dto -> modelMapperUtil.map(dto, Client.class))
                .map(clientRepository::save)
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
    public void deleteClient(long id) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ошибка: Клиент не найден."));

        var openAccountExists = client.getAccounts()
                .stream()
                .anyMatch(account -> account.getStatus() == AccountStatus.OPEN);

        if (openAccountExists) {
            throw new ClientDeletionException("Ошибка удаления клиента:" +
                    " Для совершения этого действия клиенту необходимо закрыть все открытые банковские счета.", id);
        }

        passportService.deletePassportByClientId(id);
        clientRepository.delete(client);
    }
}