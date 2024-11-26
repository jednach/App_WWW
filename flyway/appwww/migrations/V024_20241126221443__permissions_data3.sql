INSERT INTO app.permission_role (version, role_id, permission_id)
VALUES
    (0, (SELECT id FROM app.roles WHERE name = 'ADMIN'), (SELECT id FROM app.permissions WHERE name = 'REGISTRATIONS_READ'));