package ru.example.dto.account;

import lombok.Getter;
import lombok.Setter;
import ru.example.model.enums.AccountStatus;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDTO {

    private long id;

    private String accountNumber;

    private BigDecimal balance;

    private AccountStatus status;
}