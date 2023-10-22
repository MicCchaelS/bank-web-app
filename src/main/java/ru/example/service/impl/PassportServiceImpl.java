package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.dto.passport.PassportDTO;
import ru.example.exception.ResourceNotFoundException;
import ru.example.model.Client;
import ru.example.model.Passport;
import ru.example.repository.PassportRepository;
import ru.example.service.PassportService;
import ru.example.util.ModelMapperUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;

    private final ModelMapperUtil modelMapperUtil;

    @Override
    public PassportDTO findPassportByClientId(long clientId) {
        return passportRepository.findByClientId(clientId)
                .map(passport -> modelMapperUtil.map(passport, PassportDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("Ошибка: Паспорт не найден."));
    }

    @Transactional
    @Override
    public void savePassport(PassportDTO passportDTO, Client client) {
        var passport = modelMapperUtil.map(passportDTO, Passport.class);
        passport.setClient(client);
        passportRepository.save(passport);
    }

    @Transactional
    @Override
    public void updatePassport(PassportDTO passportDTO) {
        Optional.of(passportDTO)
                .map(dto -> modelMapperUtil.map(dto, Passport.class))
                .map(passportRepository::save)
                .orElseThrow();
    }

    @Transactional
    @Override
    public void deletePassportByClientId(long clientId) {
        passportRepository.findByClientId(clientId)
                .ifPresentOrElse(
                        passportRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Ошибка: Паспорт не найден.");
                        }
                );
    }
}