package ru.example.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.example.model.enums.AccountStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "balance")
    private int balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

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