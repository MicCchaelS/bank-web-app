package ru.example.dto.client;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.example.validation.AgeRange;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDTO {

    private int id;

    @Size(min = 2, max = 35, message = "Фамилия должна содержать от 2 до 35 символов.")
    @Pattern(regexp = "([А-Я]|Ё)([а-я]|ё)+", message = "Фамилия должна содержать только русские буквы " +
            "и начинаться с заглавной буквы (Пример: \"Иванов\").")
    private String lastName;

    @Size(min = 2, max = 35, message = "Имя должно содержать от 2 до 35 символов.")
    @Pattern(regexp = "([А-Я]|Ё)([а-я]|ё)+", message = "Имя должно содержать только русские буквы" +
            " и начинаться с заглавной буквы (Пример: \"Иван\").")
    private String firstName;

    @Size(min = 2, max = 35, message = "Отчество должно содержать от 2 до 35 символов.")
    @Pattern(regexp = "([А-Я]|Ё)([а-я]|ё)+", message = "Отчество должно содержать только русские буквы" +
            " и начинаться с заглавной буквы. Пример: \"Иванович\"")
    private String middleName;

    @NotNull(message = "Это поле должно быть заполнено")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd.MM.yyyy")
    @Past(message = "Дата рождения должна быть в прошлом.")
    @AgeRange(min = 14, max = 150, message = "Клиентами банка могут быть люди возрастом от 14 лет" +
            " (Максимальный возраст 150 лет).")
    private LocalDate birthdate;

    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{3} \\d{2}", message =
            "Пожалуйста, введите действительный СНИЛС " +
                    "(Пример: \"111-111-111 11\").")
    private String snils;

    @Pattern(regexp = "\\+\\d{10,12}", message = "Номер телефона должен быть записан в следующем" +
            " формате: \"+XXXXXXXXXXX\" и содержать от 10 до 12 цифр (Пример: +79876543210).")
    private String phoneNumber;
}