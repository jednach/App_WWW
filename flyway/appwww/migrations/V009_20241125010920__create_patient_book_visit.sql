CREATE TABLE IF NOT EXISTS app.patient_books (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    modified_by VARCHAR(255),
    modified_at TIMESTAMP,
    patient_info VARCHAR(255) NOT NULL,
    patient_id BIGINT UNIQUE REFERENCES app.patients(id)
);

CREATE TABLE IF NOT EXISTS app.visits (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    modified_by VARCHAR(255),
    modified_at TIMESTAMP,
    visit_date DATE NOT NULL,
    description VARCHAR(255) NOT NULL,
    patientBook_id BIGINT REFERENCES app.patient_books(id),
    doctor_id BIGINT REFERENCES app.doctors(id)
);