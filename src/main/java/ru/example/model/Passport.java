package ru.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.example.model.enums.Citizenship;
import ru.example.model.enums.Sex;

import java.time.LocalDate;

@Entity
@Table(name = "passports")
@Data
@EqualsAndHashCode(exclude = "client")
@ToString(exclude = "client")
public class Passport {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "citizenship")
    private Citizenship citizenship;

    @Column(name = "series_number")
    private String seriesNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "birthplace")
    private String birthplace;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "department_code")
    private String departmentCode;

    @Column(name = "issued_by")
    private String issuedBy;

    @Column(name = "registration_address")
    private String registrationAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
}