ALTER TABLE app.registrations
ALTER COLUMN visit_date TYPE DATE;

ALTER TABLE app.registrations
ADD COLUMN visit_time TIME NOT NULL;