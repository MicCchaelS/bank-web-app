package ru.example.service;

import ru.example.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    public List<Client> findAll();
    public Optional<Client> findById(int id);
    public Client create();
    public Optional<Client> update(int id);
    public boolean delete(int id);
}