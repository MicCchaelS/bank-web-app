package ru.example.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @DateTimeFormat(pattern = "dd.MM.yyyy")
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

    public void addAccount(Account account) {
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        accounts.add(account);
        account.setClient(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
    }
}