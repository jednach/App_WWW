INSERT INTO app.permissions (version, name, description)
VALUES
    (0, 'REGISTRATIONS_READ', 'Registration read for doctor access');

INSERT INTO app.permission_role (version, role_id, permission_id)
VALUES
    (0, (SELECT id FROM app.roles WHERE name = 'DOCTOR'), (SELECT id FROM app.permissions WHERE name = 'REGISTRATIONS_READ'));