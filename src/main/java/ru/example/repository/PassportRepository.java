package ru.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.model.Passport;

public interface PassportRepository extends JpaRepository<Passport, Integer> {

    boolean existsPassportBySeriesNumber(String seriesNumber);
    boolean existsPassportBySeriesNumberAndIdNot(String seriesNumber, int passportId);
}