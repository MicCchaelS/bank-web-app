package ru.example.dto.account;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountWithdrawDTO {

    private int id;

    private BigDecimal balance;

    @NotNull(message = "Сначала введите сумму")
    @DecimalMin(value = "0.01", message = "Минимальная сумма для списания: 0.01 ₽")
    @DecimalMax(value = "500000000", message = "Максимальная сумма для списания: 500 000 000.00 ₽")
    @Digits(integer = 11, fraction = 2, message = "Числовое значение выходит за пределы" +
            " допустимого значения (Максимум <9 цифр>.<2 цифры>)")
    private BigDecimal withdrawalAmount;
}