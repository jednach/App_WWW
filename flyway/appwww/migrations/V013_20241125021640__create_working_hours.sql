CREATE TABLE IF NOT EXISTS app.working_hours (
    id BIGSERIAL PRIMARY KEY,
    doctor_id BIGINT REFERENCES app.doctors(id),
    day_of_week VARCHAR(255) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);