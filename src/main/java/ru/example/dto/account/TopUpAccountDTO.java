package ru.example.dto.account;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.example.model.enums.AccountStatus;

import java.math.BigDecimal;

@Getter
@Setter
public class TopUpAccountDTO {

    private long id;

    private BigDecimal balance;

    private AccountStatus status;

    @NotNull(message = "Пожалуйста, укажите сумму пополнения.")
    @DecimalMin(value = "10", message = "Минимальная сумма пополнения составляет 10 ₽.")
    @DecimalMax(value = "500000000", message = "Максимальная сумма пополнения составляет 500 000 000 ₽.")
    @Digits(integer = 11, fraction = 2, message = "Числовое значение выходит за пределы допустимого диапазона" +
            " (Максимум <9 цифр>.<2 цифры>).")
    private BigDecimal replenishmentAmount;
}