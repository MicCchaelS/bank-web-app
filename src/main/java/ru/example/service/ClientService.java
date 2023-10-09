package ru.example.service;

import ru.example.dto.client.ClientDTO;
import ru.example.dto.client.ClientsDTO;

import java.util.List;

public interface ClientService {

    List<ClientsDTO> findAllClients();
    ClientDTO findClientById(int id);
    ClientDTO saveClient(ClientDTO clientDTO);
    void updateClient(ClientDTO clientDTO);
    void deleteClient(int id);
}