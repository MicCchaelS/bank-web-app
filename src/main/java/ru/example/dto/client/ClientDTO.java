package ru.example.dto.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDTO {

    private int id;

    private String lastName;

    private String firstName;

    private String middleName;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthDate;

    private String snils;
}