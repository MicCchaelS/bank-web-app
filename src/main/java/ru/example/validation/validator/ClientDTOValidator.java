package ru.example.validation.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.dto.client.ClientDTO;
import ru.example.repository.ClientRepository;

@Component
@RequiredArgsConstructor
public class ClientDTOValidator implements Validator {

    private final ClientRepository clientRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return ClientDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        var clientDTO = (ClientDTO) target;

        if (clientDTO.getId() == 0) {
            validateNewClient(clientDTO, errors);
        } else {
            validateExistingClient(clientDTO, errors);
        }
    }

    /** Валидация клиента при его создании */
    private void validateNewClient(ClientDTO clientDTO, Errors errors) {
        var snils = clientDTO.getSnils();
        var phoneNumber = clientDTO.getPhoneNumber();

        // Если СНИЛС был указан и уже используется
        if (!snils.isEmpty()) {
            if (clientRepository.existsBySnils(snils)) {
                errors.rejectValue("snils", "", "Этот СНИЛС уже используется.");
            }
        }

        // Если номер телефона был указан и уже используется
        if (!phoneNumber.isEmpty()) {
            if (clientRepository.existsByPhoneNumber(phoneNumber)) {
                errors.rejectValue("phoneNumber", "", "Этот номер телефона уже используется.");
            }
        }
    }

    /** Валидация клиента при его изменении */
    private void validateExistingClient(ClientDTO clientDTO, Errors errors) {
        var clientId = clientDTO.getId();
        var snils = clientDTO.getSnils();
        var phoneNumber = clientDTO.getPhoneNumber();

        // Если СНИЛС был указан и уже используется (без учёта текущего СНИЛСа клиента)
        if (!snils.isEmpty()) {
            if (clientRepository.existsBySnilsAndIdNot(snils, clientId)) {
                errors.rejectValue("snils", "", "Этот СНИЛС уже используется.");
            }
        }

        // Если номер телефона был указан и уже используется (без учёта текущего номера телефона клиента)
        if (!phoneNumber.isEmpty()) {
            if (clientRepository.existsByPhoneNumberAndIdNot(phoneNumber, clientId)) {
                errors.rejectValue("phoneNumber", "", "Этот номер телефона уже используется.");
            }
        }
    }
}