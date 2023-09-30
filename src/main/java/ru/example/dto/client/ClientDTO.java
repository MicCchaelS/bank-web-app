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

    @NotBlank(message = "Это поле должно быть заполнено")
    @Size(min = 2, max = 35, message = "Фамилия может содержать от 2 до 35 символов")
    @Pattern(regexp = "([А-Я]|Ё)([а-я]|ё)+", message = "Фамилия должна содержать только русские буквы " +
            "и начинаться с заглавной буквы. Пример: \"Иванов\"")
    private String lastName;

    @NotBlank(message = "Это поле должно быть заполнено")
    @Size(min = 2, max = 35, message = "Имя может содержать от 2 до 35 символов")
    @Pattern(regexp = "([А-Я]|Ё)([а-я]|ё)+", message = "Имя должно содержать только русские буквы" +
            " и начинаться с заглавной буквы. Пример: \"Иван\"")
    private String firstName;

    @NotBlank(message = "Это поле должно быть заполнено")
    @Size(min = 2, max = 35, message = "Отчество может содержать от 2 до 35 символов")
    @Pattern(regexp = "([А-Я]|Ё)([а-я]|ё)+", message = "Отчество должно содержать только русские буквы" +
            " и начинаться с заглавной буквы. Пример: \"Иванович\"")
    private String middleName;

    @NotNull(message = "Это поле должно быть заполнено")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd.MM.yyyy")
    @Past(message = "Дата рождения должна быть в прошлом")
    @AgeRange(min = 14, max = 150, message = "Клиентами банка могут быть люди возрастом от 14 лет." +
            " (Максимальный возраст 150 лет)")
    private LocalDate birthDate;

    @NotBlank(message = "Это поле должно быть заполнено")
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{3} \\d{2}", message =
            "СНИЛС должен содержать строго 14 символов (только цифры, дефисы и один пробел). " +
                    "Пример: \"111-111-111 11\"")
    private String snils;
}