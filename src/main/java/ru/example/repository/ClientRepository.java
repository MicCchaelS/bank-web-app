package ru.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    boolean existsClientBySnils(String snils);
    boolean existsClientBySnilsAndIdNot(String snils, int id);

    boolean existsClientByPhoneNumber(String phoneNumber);
    boolean existsClientByPhoneNumberAndIdNot(String phoneNumber, int id);
}