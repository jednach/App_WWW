INSERT INTO app.permissions (version, name, description)
VALUES
    (0, 'DOCTOR_CREATE', 'Doctor create access'),
    (0, 'PHARMACY_CREATE', 'Pharmacy create access');

INSERT INTO app.permission_role (version, role_id, permission_id)
VALUES
    (0, (SELECT id FROM app.roles WHERE name = 'ADMIN'), (SELECT id FROM app.permissions WHERE name = 'DOCTOR_CREATE')),
    (0, (SELECT id FROM app.roles WHERE name = 'ADMIN'), (SELECT id FROM app.permissions WHERE name = 'PHARMACY_CREATE'));