package ru.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.example.model.enums.AccountStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "accounts")
@Data
@EqualsAndHashCode(exclude = {"client", "actions", "amount"})
@ToString(exclude = {"client", "actions", "amount"})
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "balance")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

    @DecimalMin(value = "10")
    @DecimalMax(value = "500000000")
    @Digits(integer = 10, fraction = 2)/*,
            message = "Числовое значение выходит за пределы допустимого значения (максимум <10 цифр>.<2 цифры>)")*/
    @Transient
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private List<Action> actions;

    public void addAction(Action action) {
        if (actions == null) {
            actions = new ArrayList<>();
        }
        actions.add(action);
        action.setAccount(this);
    }
}