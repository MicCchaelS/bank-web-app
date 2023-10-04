package ru.example.validation.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.dto.account.AccountDTO;
import ru.example.exception.ClosedAccountException;
import ru.example.model.enums.AccountStatus;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AccountDTOValidator implements Validator {


    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return AccountDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        var accountDTO = (AccountDTO) target;

        if (accountDTO.getStatus() == AccountStatus.CLOSED) {
            throw new ClosedAccountException("Банковский счёт закрыт. Выполнение операций невозможно");
        }

        var amount = accountDTO.getAmount();
        var currentBalance = accountDTO.getBalance();

        switch (accountDTO.getOperationType()) {
            case REPLENISHMENT -> {
                var newBalance = currentBalance.add(amount);
                final var MAX_BALANCE = BigDecimal.valueOf(1_000_000_000);

                // Если после пополнения баланса новый баланс станет больше 1 000 000 000 рублей
                if (newBalance.compareTo(MAX_BALANCE) > 0) {
                    errors.rejectValue("amount", "", "Максимальная сумма," +
                            " которая может храниться на балансе счёта: 1 000 000 000 рублей");
                }
            }
            case WITHDRAWAL -> {
                // Если сумма списания больше текущего баланса
                if (amount.compareTo(currentBalance) > 0) {
                    errors.rejectValue("amount", "",
                            "На балансе недостаточно средств");
                }
            }
        }
    }
}