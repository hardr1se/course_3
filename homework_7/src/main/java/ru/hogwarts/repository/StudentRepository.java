package ru.hogwarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByFaculty_Id(long faculty_id);
    Student findById(long id);
    Collection<Student> findByAgeBetween(int from, int to);
    Collection<Student> findByAge(int age);
    @Query(value = "SELECT COUNT(*) as total_students FROM student", nativeQuery = true)
    Integer findTotalStudents();
    @Query(value = "SELECT AVG(age) as average_age FROM student", nativeQuery = true)
    Integer findAverageAgeOfStudents();
    @Query(value = "SELECT * FROM (SELECT * FROM student ORDER BY id DESC LIMIT 5) ORDER BY id ASC", nativeQuery = true)
    Collection<Student> findLastFiveStudents();
}
