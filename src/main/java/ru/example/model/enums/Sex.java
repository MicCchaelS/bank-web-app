package ru.example.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sex {
    MALE("Мужской"),
    FEMALE("Женский");

    private final String translation;
}