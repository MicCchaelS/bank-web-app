package ru.example.dto.client;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientsDTO {

    private long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private String seriesNumber;
}