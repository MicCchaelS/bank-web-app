package ru.example.validation.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.dto.passport.PassportDTO;
import ru.example.repository.PassportRepository;

@Component
@RequiredArgsConstructor
public class PassportDTOValidator implements Validator {

    private final PassportRepository passportRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return PassportDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        var passportDTO = (PassportDTO) target;

        if (passportDTO.getId() == 0) {
            validateNewPassport(passportDTO, errors);
        } else {
            validateExistingPassport(passportDTO, errors);
        }
    }

    /** Валидация паспорта при его создании */
    private void validateNewPassport(PassportDTO passportDTO, Errors errors) {
        var seriesNumber = passportDTO.getSeriesNumber();

        // Если указанные серия и номер паспорта уже используются
        if (passportRepository.existsBySeriesNumber(seriesNumber)) {
            errors.rejectValue("seriesNumber", "", "Эти серия и номер паспорта уже используется");
        }
    }

    /** Валидация паспорта при его изменении */
    private void validateExistingPassport(PassportDTO passportDTO, Errors errors) {
        var passportId = passportDTO.getId();
        var seriesNumber = passportDTO.getSeriesNumber();

        // Если указанные серия и номер паспорта уже используются (без учёта текущих серии и номера паспорта клиента)
        if (passportRepository.existsBySeriesNumberAndIdNot(seriesNumber, passportId)) {
            errors.rejectValue("seriesNumber", "", "Эти серия и номер паспорта уже используется");
        }
    }
}