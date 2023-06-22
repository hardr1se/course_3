package ru.hogwarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
