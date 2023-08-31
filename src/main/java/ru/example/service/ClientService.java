package ru.example.service;

import ru.example.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    public List<Client> findAllClients();
    public Optional<Client> findClientById(int id);
    public int saveClient(Client client);
    public void updateClient(Client client);
    public boolean deleteClient(int id);
}