package ru.example.dto.account;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import ru.example.model.enums.AccountStatus;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferAccountDTO {

    private long id;

    private String accountNumber;

    private BigDecimal balance;

    private AccountStatus status;

    @NotNull(message = "Это поле не может быть null.")
    @Pattern(regexp = "^\\d{20}$", message = "Пожалуйста, введите действительный номер счёта получателя," +
            " состоящий из 20 цифр (Пример: \"12345678901234567890\").")
    private String receiverAccountNumber;

    @NotNull(message = "Пожалуйста, укажите сумму перевода.")
    @DecimalMin(value = "10", message = "Минимальная сумма перевода составляет 10 ₽.")
    @DecimalMax(value = "500000000", message = "Максимальная сумма перевода составляет 500 000 000 ₽.")
    @Digits(integer = 11, fraction = 2, message = "Числовое значение выходит за пределы допустимого диапазона" +
            " (Максимум <9 цифр>.<2 цифры>).")
    private BigDecimal transferAmount;
}