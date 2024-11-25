CREATE TABLE IF NOT EXISTS app.registrations (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    modified_by VARCHAR(255),
    modified_at TIMESTAMP,
    visit_date TIMESTAMP NOT NULL,
    patient_id BIGINT REFERENCES app.patients(id),
    doctor_id BIGINT REFERENCES app.doctors(id)
);