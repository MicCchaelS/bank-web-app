package ru.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.example.dto.client.ClientsDTO;
import ru.example.model.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    boolean existsBySnils(String snils);
    boolean existsBySnilsAndIdNot(String snils, int clientId);

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, int clientId);

    @Query(value = """
            SELECT NEW ru.example.dto.client.ClientsDTO(
                c.id, c.lastName, c.firstName, c.middleName, p.seriesNumber
            )
            FROM Client c
            LEFT JOIN Passport p ON c.id = p.client.id
            """)
    List<ClientsDTO> findClientsWithPassports();
}