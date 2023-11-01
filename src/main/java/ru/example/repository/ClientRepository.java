package ru.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.example.dto.client.ClientsDTO;
import ru.example.dto.client.ClientsFilterDTO;
import ru.example.model.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsBySnils(String snils);
    boolean existsBySnilsAndIdNot(String snils, long clientId);

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, long clientId);

    @Query(value = """
            SELECT NEW ru.example.dto.client.ClientsDTO(
                c.id, c.lastName, c.firstName, c.middleName, p.seriesNumber
            )
            FROM Client c
            LEFT JOIN Passport p ON c.id = p.client.id
            """)
    List<ClientsDTO> findAllClientsAndPassports();

    @Query(value = """
            SELECT NEW ru.example.dto.client.ClientsDTO(
                c.id, c.lastName, c.firstName, c.middleName, p.seriesNumber
            )
            FROM Client c
            LEFT JOIN Passport p ON c.id = p.client.id
            WHERE (c.lastName LIKE %:#{#clientsFilterDTO.lastName}% OR :#{#clientsFilterDTO.lastName} IS NULL)
            AND (c.firstName LIKE %:#{#clientsFilterDTO.firstName}% OR :#{#clientsFilterDTO.firstName} IS NULL)
            AND (c.middleName LIKE %:#{#clientsFilterDTO.middleName}% OR :#{#clientsFilterDTO.middleName} IS NULL)
            AND (p.seriesNumber LIKE %:#{#clientsFilterDTO.seriesNumber}% OR :#{#clientsFilterDTO.seriesNumber} IS NULL)
            """)
    List<ClientsDTO> findAllClientsAndPassportsByFilter(ClientsFilterDTO clientsFilterDTO);
}