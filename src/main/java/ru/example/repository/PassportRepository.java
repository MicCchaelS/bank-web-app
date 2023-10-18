package ru.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.model.Passport;

import java.util.Optional;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Integer> {

    Optional<Passport> findByClientId(int clientId);

    boolean existsBySeriesNumber(String seriesNumber);
    boolean existsBySeriesNumberAndIdNot(String seriesNumber, int passportId);
}