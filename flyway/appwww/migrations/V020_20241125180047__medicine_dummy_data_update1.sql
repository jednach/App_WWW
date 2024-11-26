UPDATE app.medicines
SET medicine_type = REPLACE(medicine_type, '-', '_');
