package ru.example.validation.validator.account;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.dto.account.TopUpAccountDTO;
import ru.example.exception.account.ClosedAccountException;
import ru.example.model.enums.AccountStatus;

import java.math.BigDecimal;

@Component
public class TopUpAccountDTOValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return TopUpAccountDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        var accountDTO = (TopUpAccountDTO) target;

        if (accountDTO.getStatus() == AccountStatus.CLOSED) {
            throw new ClosedAccountException("Ошибка: Банковский счёт закрыт, выполнение операций невозможно.");
        }

        var replenishmentAmount = accountDTO.getReplenishmentAmount();
        var currentBalance = accountDTO.getBalance();

        var newBalance = currentBalance.add(replenishmentAmount);
        final var MAX_BALANCE = BigDecimal.valueOf(1_000_000_000);

        // Если сумма после пополнения превысит максимально допустимый баланс - 1 000 000 000 рублей
        if (newBalance.compareTo(MAX_BALANCE) > 0) {
            errors.rejectValue("replenishmentAmount", "", "Невозможно пополнить счёт этой" +
                    " суммой, так как после пополнения баланс превысит максимально допустимый лимит в 1 000 000 000 ₽.");
        }
    }
}