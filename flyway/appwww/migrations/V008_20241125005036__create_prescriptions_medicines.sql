CREATE TABLE IF NOT EXISTS app.medicines (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    modified_by VARCHAR(255),
    modified_at TIMESTAMP,
    medicine_name VARCHAR(255) NOT NULL,
    medicine_type VARCHAR(255) NOT NULL,
    medicine_description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS app.prescriptions (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    modified_by VARCHAR(255),
    modified_at TIMESTAMP,
    realized BOOLEAN NOT NULL,
    patient_id BIGINT REFERENCES app.patients(id),
    doctor_id BIGINT REFERENCES app.doctors(id),
    realizedBy_pharmacy_id BIGINT REFERENCES app.pharmacies(id)
);

CREATE TABLE IF NOT EXISTS app.prescription_medicine (
    prescription_id BIGINT REFERENCES app.prescriptions(id) ON DELETE CASCADE,
    medicine_id BIGINT REFERENCES app.medicines(id) ON DELETE CASCADE,
    PRIMARY KEY (prescription_id, medicine_id)
);