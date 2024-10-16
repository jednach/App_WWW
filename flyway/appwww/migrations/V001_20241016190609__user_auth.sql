CREATE TABLE IF NOT EXISTS app.users (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    modified_by VARCHAR(255),
    modified_at TIMESTAMP,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS app.roles (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    modified_by VARCHAR(255),
    modified_at TIMESTAMP,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS app.permissions (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    modified_by VARCHAR(255),
    modified_at TIMESTAMP,
    name VARCHAR(255) UNIQUE NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS app.permission_role (
    role_id BIGINT REFERENCES app.roles(id),
    permission_id BIGINT REFERENCES app.permissions(id),
    version BIGINT,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    modify_by VARCHAR(255),
    modified_at TIMESTAMP,
    PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE IF NOT EXISTS app.role_user (
    user_id BIGINT REFERENCES app.users(id),
    role_id BIGINT REFERENCES app.roles(id),
    version BIGINT,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    modify_by VARCHAR(255),
    modified_at TIMESTAMP,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS app.refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    modified_by VARCHAR(255),
    modified_at TIMESTAMP,
    user_id BIGINT REFERENCES app.users(id),
    token VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS app.verification_tokens (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    modified_by VARCHAR(255),
    modified_at TIMESTAMP,
    user_id BIGINT REFERENCES app.users(id),
    token VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL
);