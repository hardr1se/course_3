ALTER TABLE student ADD CONSTRAINT age_constraint CHECK (age >= 16);

ALTER TABLE student ADD CONSTRAINT name_constraint UNIQUE (name), ALTER COLUMN name SET NOT NULL;

ALTER TABLE faculty ADD CONSTRAINT unique_pair UNIQUE (name, colour);

ALTER TABLE student ALTER COLUMN age SET DEFAULT 21;