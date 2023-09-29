package ru.example.dto.account;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;
import ru.example.model.enums.AccountStatus;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDTO {

    private int id;

    private String accountNumber;

    private BigDecimal balance;

    private AccountStatus status;

    @DecimalMin(value = "10")
    @DecimalMax(value = "500000000")
    @Digits(integer = 10, fraction = 2)/*,
            message = "Числовое значение выходит за пределы допустимого значения (максимум <10 цифр>.<2 цифры>)")*/
    private BigDecimal amount;
}