package ru.example.service;

import ru.example.dto.passport.PassportDTO;
import ru.example.model.Client;

public interface PassportService {

    PassportDTO findPassportByClientId(int clientId);
    void savePassport(PassportDTO passportDTO, Client client);
    void updatePassport(PassportDTO passportDTO);
    void deletePassportByClientId(int clientId);
}