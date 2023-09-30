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
    public void validate(@NonNull Object target,@NonNull Errors errors) {
        var clientDTO = (ClientDTO) target;

        if (clientDTO.getSnils().isBlank()) {
            return;
        }

        // Если при создании клиента введённый СНИЛС уже используется
        if (clientDTO.getId() == 0 && clientRepository.existsClientBySnils(clientDTO.getSnils())) {
            errors.rejectValue("snils", "", "Этот СНИЛС уже используется");
        }

        // Если при изменении данных клиента введённый СНИЛС уже используется (без учёта текущего СНИЛСа клиента)
        if (clientDTO.getId() != 0 && clientRepository.existsClientBySnilsAndIdNot(
                clientDTO.getSnils(), clientDTO.getId())) {
            errors.rejectValue("snils", "", "Этот СНИЛС уже используется");
        }
    }
}