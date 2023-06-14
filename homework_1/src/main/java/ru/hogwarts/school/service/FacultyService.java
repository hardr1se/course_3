package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

@Service
public class FacultyService {
    private HashMap<Long, Faculty> faculties = new HashMap<>();
    private long id = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++id);
        faculties.put(id, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    public Collection<Faculty> getAllFaculties() {
        return Collections.unmodifiableCollection(faculties.values());
    }

    public Collection<Faculty> getFacultiesByColour(String colour) {
        return faculties.values().stream()
                .filter(x -> x.getColour().equals(colour))
                .toList();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return faculties.put(faculty.getId(), faculty);
    }

    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }
}
