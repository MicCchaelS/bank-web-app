package ru.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "snils")
    private String snils;

    @OneToMany(mappedBy = "client", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    private List<Account> accounts;

    public Client() {
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
        account.setClient(this);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Client;
    }

}