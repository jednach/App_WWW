INSERT INTO app.roles (version, name)
VALUES
    (0, 'ADMIN'),
    (0, 'USER');

INSERT INTO app.permissions (version, name, description)
VALUES
    (0, 'READ', 'Read access'),
    (0, 'CREATE', 'Create access'),
    (0, 'UPDATE', 'Update access'),
    (0, 'DELETE', 'Delete access');

INSERT INTO app.permission_role (version, role_id, permission_id)
VALUES
    (0, (SELECT id FROM app.roles WHERE name = 'ADMIN'), (SELECT id FROM app.permissions WHERE name = 'READ')),
    (0, (SELECT id FROM app.roles WHERE name = 'ADMIN'), (SELECT id FROM app.permissions WHERE name = 'CREATE')),
    (0, (SELECT id FROM app.roles WHERE name = 'ADMIN'), (SELECT id FROM app.permissions WHERE name = 'UPDATE')),
    (0, (SELECT id FROM app.roles WHERE name = 'ADMIN'), (SELECT id FROM app.permissions WHERE name = 'DELETE')),
    (0, (SELECT id FROM app.roles WHERE name = 'USER'), (SELECT id FROM app.permissions WHERE name = 'READ')),
    (0, (SELECT id FROM app.roles WHERE name = 'USER'), (SELECT id FROM app.permissions WHERE name = 'CREATE'));