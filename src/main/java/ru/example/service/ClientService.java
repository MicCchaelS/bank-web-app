package ru.example.service;

import ru.example.dto.ClientDTO;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    public List<ClientDTO> findAllClients();
    public Optional<ClientDTO> findClientById(int id);
    public ClientDTO saveClient(ClientDTO clientDTO);
    public void updateClient(ClientDTO clientDTO);
    public boolean deleteClient(int id);
}