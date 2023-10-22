ALTER TABLE actions
    ADD COLUMN sender_account_id   BIGINT, -- id банковского счёта отправителя (заполняется только при совершении перевода)
    ADD COLUMN receiver_account_id BIGINT; -- id банковского счёта получателя (заполняется только при совершении перевода)