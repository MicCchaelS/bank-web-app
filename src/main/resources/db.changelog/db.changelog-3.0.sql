CREATE TABLE passports
(
    id                   BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, -- id паспорта
    citizenship          CHAR(2),                                         -- Двухбуквенный код гражданства страны
    series_number        VARCHAR(50) UNIQUE,                              -- Серия и номер паспорта
    sex                  VARCHAR(7),                                      -- Пол
    birthplace           VARCHAR(255),                                    -- Место рождения
    issue_date           DATE,                                            -- Дата выдачи паспорта
    department_code      VARCHAR(50),                                     -- Код подразделения
    issued_by            VARCHAR(255),                                    -- Кем выдан
    registration_address VARCHAR(255),                                    -- Адрес регистрации
    client_id            BIGINT NOT NULL UNIQUE REFERENCES clients (id)   -- Уникальный id клиента, которому принадлежит паспорт
);