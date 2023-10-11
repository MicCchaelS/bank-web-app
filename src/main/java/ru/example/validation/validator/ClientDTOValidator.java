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

        // Если при создании клиента
        if (clientDTO.getId() == 0) {
            // введённый СНИЛС уже используется
            if (clientRepository.existsClientBySnils(clientDTO.getSnils())) {
                errors.rejectValue("snils", "", "Этот СНИЛС уже используется");
            }

            // номер телефона уже используется
            if (clientRepository.existsClientByPhoneNumber(clientDTO.getPhoneNumber())) {
                errors.rejectValue("phoneNumber", "", "Этот номер телефона уже используется");
            }
        }

        // Если при изменении данных клиента
        if (clientDTO.getId() != 0) {
            // введённый СНИЛС уже используется (без учёта текущего СНИЛСа клиента)
            if (clientRepository.existsClientBySnilsAndIdNot(clientDTO.getSnils(), clientDTO.getId())) {
                errors.rejectValue("snils", "", "Этот СНИЛС уже используется");
            }

            // введённый номер телефона уже используется (без учёта текущего номера телефона клиента)
            if (clientRepository.existsClientByPhoneNumberAndIdNot(clientDTO.getPhoneNumber(), clientDTO.getId())) {
                errors.rejectValue("phoneNumber", "", "Этот номер телефона уже используется");
            }
        }
    }
}