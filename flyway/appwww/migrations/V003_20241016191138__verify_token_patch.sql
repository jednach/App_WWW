ALTER TABLE app.verification_tokens
    ALTER COLUMN token DROP NOT NULL,
    ALTER COLUMN expiry_date DROP NOT NULL;