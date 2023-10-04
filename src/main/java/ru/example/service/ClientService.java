package ru.example.service;

import ru.example.dto.client.ClientDTO;
import ru.example.dto.client.ClientsDTO;

import java.util.List;

public interface ClientService {

    public List<ClientsDTO> findAllClients();
    public ClientDTO findClientById(int id);
    public ClientDTO saveClient(ClientDTO clientDTO);
    public void updateClient(ClientDTO clientDTO);
    public void deleteClient(int id);
}