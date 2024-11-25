CREATE TABLE IF NOT EXISTS app.patients (
    id BIGINT PRIMARY KEY REFERENCES app.users(id),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    gender BOOLEAN NOT NULL,
    pesel_number VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS app.doctors (
    id BIGINT PRIMARY KEY REFERENCES app.users(id),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS app.pharmacies (
    id BIGINT PRIMARY KEY REFERENCES app.users(id),
    pharmacy_name VARCHAR(255) NOT NULL,
    pharmacy_address VARCHAR(255) NOT NULL,
    pharmacy_city VARCHAR(255) NOT NULL
);