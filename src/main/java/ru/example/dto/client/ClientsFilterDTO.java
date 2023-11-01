package ru.example.dto.client;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientsFilterDTO {

    @Pattern(regexp = "^$|^[А-ЯЁ]?[а-яё]{1,34}$", message = "Для фильтрации или поиска клиентов по фамилии введите" +
            " от 1 до 35 символов, только русские буквы, заглавная буква только первая (Пример: \"Иванов\").")
    private String lastName;

    @Pattern(regexp = "^$|^[А-ЯЁ]?[а-яё]{1,34}$", message = "Для фильтрации или поиска клиентов по имени введите" +
            " от 1 до 35 символов, только русские буквы, заглавная буква только первая (Пример: \"Иван\").")
    private String firstName;

    @Pattern(regexp = "^$|^[А-ЯЁ]?[а-яё]{1,34}$", message = "Для фильтрации или поиска клиентов по отчеству введите" +
            " от 1 до 35 символов, только русские буквы, заглавная буква только первая (Пример: \"Иванович\").")
    private String middleName;

    @Pattern(regexp = "^$|^\\d{2} \\d{2}$|^\\d{6}$|^\\d{2} \\d{2} \\d{6}$", message = "Фильтровать или найти клиентов" +
            " можно только по серии (\"11 22\"), по номеру (\"333444\") или по серии и номеру (\"11 22 333444\").")
    private String seriesNumber;
}