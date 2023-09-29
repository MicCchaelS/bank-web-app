package ru.example.dto.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientsDTO {

    private int id;

    private String lastName;

    private String firstName;

    private String middleName;
}