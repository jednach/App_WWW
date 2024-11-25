ALTER TABLE app.working_hours 
ADD COLUMN version BIGINT,
ADD COLUMN created_by VARCHAR(255),
ADD COLUMN created_at TIMESTAMP,
ADD COLUMN modified_by VARCHAR(255),
ADD COLUMN modified_at TIMESTAMP;
