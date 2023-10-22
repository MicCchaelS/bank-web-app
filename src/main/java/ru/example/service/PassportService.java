package ru.example.service;

import ru.example.dto.passport.PassportDTO;
import ru.example.model.Client;

public interface PassportService {

    PassportDTO findPassportByClientId(long clientId);
    void savePassport(PassportDTO passportDTO, Client client);
    void updatePassport(PassportDTO passportDTO);
    void deletePassportByClientId(long clientId);
}