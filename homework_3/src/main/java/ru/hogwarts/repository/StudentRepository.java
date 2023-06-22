package ru.hogwarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> getAllByAge(int age);
    Collection<Student> findByAgeBetween(int min, int max);
    Student findStudentById(long id);
}
