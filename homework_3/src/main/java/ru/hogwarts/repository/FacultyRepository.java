package ru.hogwarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.model.Faculty;
import ru.hogwarts.model.Student;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> getAllByColourIgnoreCase(String colour);
    Collection<Faculty> getAllByNameIgnoreCase(String name);
    Faculty getFacultyById(long id);
}
