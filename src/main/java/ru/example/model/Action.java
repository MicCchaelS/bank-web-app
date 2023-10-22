package ru.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.example.model.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "actions")
@Data
@EqualsAndHashCode(exclude = "account")
@ToString(exclude = "account")
public class Action {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "reminder")
    private BigDecimal reminder;

    @Column(name = "operation_date_time")
    private LocalDateTime operationDateTime;

    @Column(name = "sender_account_id")
    private Long senderAccountId;

    @Column(name = "receiver_account_id")
    private Long receiverAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}