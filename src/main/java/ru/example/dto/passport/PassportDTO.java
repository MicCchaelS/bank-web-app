package ru.example.dto.passport;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.example.model.enums.Citizenship;
import ru.example.model.enums.Sex;

import java.time.LocalDate;

@Getter
@Setter
public class PassportDTO {

    private int id;

    @Pattern(regexp = "^[A-Z]{2}$", message = "Это поле должно содержать двухбуквенный код страны, " +
            "состоящий из двух латинских прописных букв (Пример: \"RU\").")
    private Citizenship citizenship;

    @Pattern(regexp = "^\\d{2} \\d{2} \\d{6}$", message = "Пожалуйста, введите действительные " +
            "серию и номер паспорта в следующем формате: \"XX XX XXXXXX\" (Пример: \"11 22 333444\").")
    private String seriesNumber;

    @Pattern(regexp = "^(MALE|FEMALE)$", message = "Пол может быть только \"MALE\" или \"FEMALE\".")
    private Sex sex;

    @NotBlank(message = "Это поле должно быть заполнено")
    @Size(min = 3, max = 255, message = "Место рождение должно содержать от 3 до 255 символов")
    private String birthplace;

    @NotNull(message = "Это поле должно быть заполнено")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd.MM.yyyy")
    @Past(message = "Дата выдачи паспорта должна быть в прошлом.")
    private LocalDate issueDate;

    @Pattern(regexp = "^\\d{3}-\\d{3}$", message = "Пожалуйста, введите действительный " +
            "код подразделения в следующем формате: \"XXX-XXX\" (Пример: \"123-456\").")
    private String departmentCode;

    @NotBlank(message = "Это поле должно быть заполнено")
    @Size(min = 2, max = 255, message = "Поле кем выдан паспорт должно содержать от 2 до 255 символов")
    private String issuedBy;

    @NotBlank(message = "Это поле должно быть заполнено")
    @Size(min = 4, max = 255, message = "Адрес регистрации должен содержать от 4 до 255 символов")
    private String registrationAddress;
}