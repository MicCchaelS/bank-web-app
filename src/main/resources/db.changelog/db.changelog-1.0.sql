CREATE TABLE clients
(
    id          INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY, -- id клиента
    last_name   VARCHAR(50),                                      -- Фамилия
    first_name  VARCHAR(50),                                      -- Имя
    middle_name VARCHAR(50),                                      -- Отчество
    birthdate   DATE,                                             -- Дата рождения
    snils       VARCHAR(14) UNIQUE                                -- СНИЛС
);

CREATE TABLE accounts
(
    id             INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY, -- id банковского счёта
    account_number CHAR(20) UNIQUE,                                  -- Номер счёта
    balance        NUMERIC(12, 2),                                   -- Баланс
    status         VARCHAR(6),                                       -- Статус счёта
    client_id      INTEGER REFERENCES clients (id)                   -- id владельца счёта
);

CREATE TABLE actions
(
    id                  INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY, -- id операции
    operation_type      VARCHAR(50),                                      -- Тип операции
    amount              NUMERIC(12, 2),                                   -- Сумма
    reminder            NUMERIC(12, 2),                                   -- Остаток на счёте
    operation_date_time TIMESTAMP,                                        -- Дата и время операции
    account_id          INTEGER NOT NULL REFERENCES accounts (id)         -- id счёта, на котором совершена операция
);