package ru.example.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.example.model.enums.OperationType;

import java.time.LocalDateTime;

@Entity
@Table(name = "actions")
@Data
public class Action {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;

    @Column(name = "amount")
    private int amount;

    @Column(name = "reminder")
    private int reminder;

    @Column(name = "operation_date")
    private LocalDateTime operationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}