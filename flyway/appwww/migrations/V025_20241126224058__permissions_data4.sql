INSERT INTO app.permission_role (version, role_id, permission_id)
VALUES
    (0, (SELECT id FROM app.roles WHERE name = 'PHARMACY'), (SELECT id FROM app.permissions WHERE name = 'MEDICINE_READ'));