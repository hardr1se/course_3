package ru.hogwarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByFaculty_Id(long faculty_id);
    Student findById(long id);
    Collection<Student> findByAgeBetween(int from, int to);
    Collection<Student> findByAge(int age);
}
