package ru.example.dto.client;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.example.validation.annotation.AgeRange;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDTO {

    private int id;

    @Pattern(regexp = "^[А-ЯЁ][а-яё]{1,34}$", message = "Фамилия должна содержать от 2 до 35 символов, " +
            "только русские буквы и начинаться с заглавной буквы (Пример: \"Иванов\").")
    private String lastName;

    @Pattern(regexp = "^[А-ЯЁ][а-яё]{1,34}$", message = "Имя должно содержать от 2 до 35 символов, " +
            "только русские буквы и начинаться с заглавной буквы (Пример: \"Иван\").")
    private String firstName;

    @NotNull(message = "Это поле не может быть null")
    @Pattern(regexp = "^$|^[А-ЯЁ][а-яё]{1,34}$", message = "Отчество (при его наличии) должно содержать " +
            "от 2 до 35 символов, только русские буквы и начинаться с заглавной буквы (Пример: \"Иванович\").")
    private String middleName;

    @NotNull(message = "Это поле должно быть заполнено")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd.MM.yyyy")
    @Past(message = "Дата рождения должна быть в прошлом.")
    @AgeRange(min = 14, message = "Клиентами банка могут быть люди возрастом от 14 лет " +
            "(Максимальный возраст 150 лет).")
    private LocalDate birthdate;

    @NotNull(message = "Это поле не может быть null")
    @Pattern(regexp = "^$|^\\d{3}-\\d{3}-\\d{3} \\d{2}$", message = "Пожалуйста, введите действительный СНИЛС " +
                    "в следующем формате: \"XXX-XXX-XXX XX\" (Пример: \"111-111-111 11\").")
    private String snils;

    @NotNull(message = "Это поле не может быть null")
    @Pattern(regexp = "^$|^\\+\\d{10,12}$", message = "Номер телефона должен быть записан в следующем " +
            "формате: \"+XXXXXXXXXXX\" и содержать от 10 до 12 цифр (Пример: +79876543210).")
    private String phoneNumber;
}