package ru.example.dto.account;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.example.model.enums.AccountStatus;
import ru.example.model.enums.OperationType;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDTO {

    private int id;

    private String accountNumber;

    private BigDecimal balance;

    private AccountStatus status;

    @NotNull(message = "Сначала введите сумму")
    @DecimalMin(value = "1", message = "Минимальная сумма для пополнения или списания: 1 ₽")
    @DecimalMax(value = "500000000", message = "Максимальная сумма для пополнения или списания: 500 000 000 ₽")
    @Digits(integer = 11, fraction = 2, message = "Числовое значение выходит за пределы" +
            " допустимого значения (Максимум <9 цифр>.<2 цифры>)")
    private BigDecimal amount;

    private OperationType operationType;
}