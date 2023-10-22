package ru.example.validation.validator.account;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.dto.account.TransferAccountDTO;
import ru.example.exception.account.ClosedAccountException;
import ru.example.model.enums.AccountStatus;

@Component
public class TransferAccountDTOValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return TransferAccountDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        var accountDTO = (TransferAccountDTO) target;

        if (accountDTO.getStatus() == AccountStatus.CLOSED) {
            throw new ClosedAccountException("Ошибка: Банковский счёт закрыт, выполнение операций невозможно.");
        }

        if (accountDTO.getAccountNumber().equals(accountDTO.getReceiverAccountNumber())) {
            errors.rejectValue("receiverAccountNumber", "", "Был указан текущий номер" +
                    " счёта клиента в качестве получателя. Пожалуйста, укажите корректный номер счёта получателя" +
                    " для завершения операции перевода.");
        }

        var transferAmount = accountDTO.getTransferAmount();
        var currentBalance = accountDTO.getBalance();

        if (transferAmount.compareTo(currentBalance) > 0) {
            errors.rejectValue("transferAmount", "", "На балансе недостаточно средств.");
        }
    }
}