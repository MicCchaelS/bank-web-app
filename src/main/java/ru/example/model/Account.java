package ru.example.model;

import jakarta.persistence.*;
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
@EqualsAndHashCode(exclude = {"client", "actions"})
@ToString(exclude = {"client", "actions"})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Action> actions;

    public void addAction(Action action) {
        if (actions == null) {
            actions = new ArrayList<>();
        }
        actions.add(action);
        action.setAccount(this);
    }
}