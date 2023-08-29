package ru.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.model.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Integer> {
}