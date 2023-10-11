CREATE TABLE passports
(
    id                   INTEGER PRIMARY KEY REFERENCES clients (id), -- id паспорта и клиента, которому он принадлежит
    citizenship          CHAR(2),                                     -- Двухбуквенный код гражданства страны
    series_number        VARCHAR(50) UNIQUE,                          -- Серия и номер паспорта
    sex                  VARCHAR(7),                                  -- Пол
    birthplace           VARCHAR(255),                                -- Место рождения
    issue_date           DATE,                                        -- Дата выдачи паспорта
    department_code      VARCHAR(50),                                 -- Код подразделения
    issued_by            VARCHAR(255),                                -- Кем выдан
    registration_address VARCHAR(255)                                 -- Адрес регистрации
);