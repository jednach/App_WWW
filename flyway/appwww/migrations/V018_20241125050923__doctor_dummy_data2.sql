INSERT INTO app.users (version, type, email, password, enabled, phone_number)
VALUES
    (0, 'DOCTOR', 'doctor2@gmail.com', '$2a$10$JtBVOUwbuMN1l4YiHIGckOAjyPpb2rd6KSeQrEstx1cgkje4chxky', true, '123123124');

INSERT INTO app.verification_tokens (version, user_id, token, expiry_date)
VALUES
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), NULL, NULL);

INSERT INTO app.role_user (version, user_id, role_id)
VALUES
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), (SELECT id FROM app.roles WHERE name = 'DOCTOR'));

INSERT INTO app.doctors (id, first_name, last_name)
VALUES
    ((SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), 'doctor', 'dummy2');

INSERT INTO app.working_hours (version, doctor_id, day_of_week, start_time, end_time)
VALUES
    -- Poniedziałek
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), 'MONDAY', '08:15', '11:00'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), 'MONDAY', '11:30', '13:15'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), 'MONDAY', '13:45', '14:45'),

    -- Wtorek
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), 'TUESDAY', '08:15', '11:00'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), 'TUESDAY', '11:30', '13:30'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), 'TUESDAY', '14:00', '15:00'),

    -- Środa
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), 'WEDNESDAY', '08:00', '11:00'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), 'WEDNESDAY', '11:30', '13:30'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), 'WEDNESDAY', '13:45', '14:45'),

    -- Czwartek
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), 'THURSDAY', '08:30', '11:00'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), 'THURSDAY', '14:00', '15:00'),

    -- Piątek
    (0, (SELECT id FROM app.users WHERE email = 'doctor2@gmail.com'), 'FRIDAY', '09:00', '14:00');