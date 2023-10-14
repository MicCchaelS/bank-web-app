package ru.example.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Citizenship {
    RU("Российская Федерация");

    private final String countryName;
}