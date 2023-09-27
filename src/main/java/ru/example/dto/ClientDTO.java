package ru.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDTO {

    private int id;

    private String lastName;

    private String firstName;

    private String middleName;

    private LocalDate birthDate;

    private String snils;
}