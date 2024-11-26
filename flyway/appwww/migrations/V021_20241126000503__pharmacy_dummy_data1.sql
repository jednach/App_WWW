INSERT INTO app.users (version, type, email, password, enabled, phone_number)
VALUES
    (0, 'PHARMACY', 'pharmacy1@gmail.com', '$2a$10$JtBVOUwbuMN1l4YiHIGckOAjyPpb2rd6KSeQrEstx1cgkje4chxky', true, '123321123');

INSERT INTO app.verification_tokens (version, user_id, token, expiry_date)
VALUES
    (0, (SELECT id FROM app.users WHERE email = 'pharmacy1@gmail.com'), NULL, NULL);

INSERT INTO app.role_user (version, user_id, role_id)
VALUES
    (0, (SELECT id FROM app.users WHERE email = 'pharmacy1@gmail.com'), (SELECT id FROM app.roles WHERE name = 'PHARMACY'));

INSERT INTO app.pharmacies (id, pharmacy_name, pharmacy_address, pharmacy_city)
VALUES
    ((SELECT id FROM app.users WHERE email = 'pharmacy1@gmail.com'), 'pharmacy1', 'Kołobrzeska 1', 'Olsztyn');