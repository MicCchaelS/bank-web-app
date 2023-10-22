package ru.example.validation.validator.account;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.dto.account.WithdrawAccountDTO;
import ru.example.exception.account.ClosedAccountException;
import ru.example.model.enums.AccountStatus;

@Component
public class WithdrawAccountDTOValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return WithdrawAccountDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        var accountDTO = (WithdrawAccountDTO) target;

        if (accountDTO.getStatus() == AccountStatus.CLOSED) {
            throw new ClosedAccountException("Ошибка: Банковский счёт закрыт, выполнение операций невозможно.");
        }

        var withdrawalAmount = accountDTO.getWithdrawalAmount();
        var currentBalance = accountDTO.getBalance();

        if (withdrawalAmount.compareTo(currentBalance) > 0) {
            errors.rejectValue("withdrawalAmount", "", "На балансе недостаточно средств.");
        }
    }
}