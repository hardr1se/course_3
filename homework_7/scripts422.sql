CREATE TABLE drivers (
    id SERIAL PRIMARY KEY,
    last_name VARCHAR NOT NULL,
    age INT NOT NULL,
    driver_license BOOLEAN DEFAULT FALSE,
    car_id INT,
    FOREIGN KEY (car_id) REFERENCES cars (id)
);

CREATE TABLE cars (
    id SERIAL PRIMARY KEY,
    model VARCHAR NOT NULL,
    brand VARCHAR NOT NULL,
    price NUMERIC NOT NULL
);

SELECT student.name, student.age, faculty.name
FROM student
FULL OUTER JOIN faculty ON student.faculty_id = faculty.id;

SELECT student.*
FROM student
LEFT JOIN avatar ON student.id = avatar.student_id
WHERE avatar.id IS NOT NULL;