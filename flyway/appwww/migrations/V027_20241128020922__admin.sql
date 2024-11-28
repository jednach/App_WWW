INSERT INTO app.users (version, type, email, password, enabled, phone_number)
VALUES
    (0, 'ADMIN', 'admin@admin.pl', '$2a$10$JtBVOUwbuMN1l4YiHIGckOAjyPpb2rd6KSeQrEstx1cgkje4chxky', true, '777666777');

INSERT INTO app.verification_tokens (version, user_id, token, expiry_date)
VALUES
    (0, (SELECT id FROM app.users WHERE email = 'admin@admin.pl'), NULL, NULL);

INSERT INTO app.role_user (version, user_id, role_id)
VALUES
    (0, (SELECT id FROM app.users WHERE email = 'admin@admin.pl'), (SELECT id FROM app.roles WHERE name = 'ADMIN'));