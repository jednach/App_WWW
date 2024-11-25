INSERT INTO app.working_hours (version, doctor_id, day_of_week, start_time, end_time)
VALUES
    -- Poniedziałek
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'MONDAY', '08:00', '11:00'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'MONDAY', '11:30', '13:30'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'MONDAY', '14:00', '15:00'),

    -- Wtorek
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'TUESDAY', '08:00', '11:00'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'TUESDAY', '11:30', '13:30'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'TUESDAY', '14:00', '15:00'),

    -- Środa
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'WEDNESDAY', '08:00', '11:00'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'WEDNESDAY', '11:30', '13:30'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'WEDNESDAY', '14:00', '15:00'),

    -- Czwartek
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'THURSDAY', '08:00', '11:00'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'THURSDAY', '11:30', '13:30'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'THURSDAY', '14:00', '15:00'),

    -- Piątek
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'FRIDAY', '08:00', '11:00'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'FRIDAY', '11:30', '13:30'),
    (0, (SELECT id FROM app.users WHERE email = 'doctor1@gmail.com'), 'FRIDAY', '14:00', '15:00');
