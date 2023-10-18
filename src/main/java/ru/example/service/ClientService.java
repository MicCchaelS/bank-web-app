package ru.example.service;

import ru.example.dto.client.ClientDTO;
import ru.example.dto.client.ClientsDTO;
import ru.example.model.Client;

import java.util.List;

public interface ClientService {

    List<ClientsDTO> findSpecificClientsPassportsFields();
    ClientDTO findClientById(int id);
    Client saveClient(ClientDTO clientDTO);
    void updateClient(ClientDTO clientDTO);
    void deleteClient(int id);
}