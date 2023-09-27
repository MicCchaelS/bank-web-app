package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dto.ClientDTO;
import ru.example.model.Client;
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
    public List<ClientDTO> findAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(client -> modelMapperUtil.map(client, ClientDTO.class))
                .toList();
    }

    @Override
    public Optional<ClientDTO> findClientById(int id) {
        return clientRepository.findById(id)
                .map(client -> modelMapperUtil.map(client, ClientDTO.class));
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
        clientRepository.save(modelMapperUtil.map(clientDTO, Client.class));
    }

    @Transactional
    @Override
    public boolean deleteClient(int id) {
        return clientRepository.findById(id)
                .map(client -> {
                    clientRepository.delete(client);
                    return true;
                })
                .orElse(false);
    }
}