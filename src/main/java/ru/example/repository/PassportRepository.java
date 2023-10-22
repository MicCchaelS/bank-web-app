package ru.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.model.Passport;

import java.util.Optional;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {

    Optional<Passport> findByClientId(long clientId);

    boolean existsBySeriesNumber(String seriesNumber);
    boolean existsBySeriesNumberAndIdNot(String seriesNumber, long passportId);
}