package ru.example.service;

import ru.example.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    public List<Client> findAll();
    public Optional<Client> findById(int id);
    public int create(Client client);
    public void update(Client client);
    public boolean delete(int id);
}