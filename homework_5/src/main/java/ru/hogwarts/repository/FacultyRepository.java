package ru.hogwarts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> getAllByColourIgnoreCaseOrNameIgnoreCase(String colour, String name);
    Faculty getFacultyById(long id);
}