package ru.example.service;

import ru.example.dto.client.ClientDTO;
import ru.example.dto.client.ClientsDTO;
import ru.example.dto.client.ClientsFilterDTO;
import ru.example.model.Client;

import java.util.List;

public interface ClientService {

    List<ClientsDTO> findAllClientsAndPassports();
    List<ClientsDTO> findAllClientsAndPassportsByFilter(ClientsFilterDTO clientsFilterDTO);
    ClientDTO findClientById(long id);
    Client saveClient(ClientDTO clientDTO);
    void updateClient(ClientDTO clientDTO);
    void deleteClient(long id);
}