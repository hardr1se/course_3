package ru.hogwarts.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.model.Faculty;
import ru.hogwarts.repository.FacultyRepository;

import java.util.Collection;
import java.util.Collections;
@Service
public class FacultyService {
    private FacultyRepository facultyRepository;
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }
    public Faculty create(Faculty faculty) {
        return facultyRepository.save(faculty);
    }
    public Faculty find(long id) {
        return facultyRepository.findById(id).get();
    }
    public Collection<Faculty> getAll() {
        return Collections.unmodifiableCollection(facultyRepository.findAll());
    }

    public Collection<Faculty> getFacultiesByColour(String colour) {
        return facultyRepository.getAllByColourIgnoreCase(colour);
    }

    public Collection<Faculty> getFacultiesByName(String name) {
        return facultyRepository.getAllByNameIgnoreCase(name);
    }

    public Faculty getFacultiesByFaculty_IdOfStudent(Long facultyId) {
        return facultyRepository.getFacultyById(facultyId);
    }

    public Faculty update(Faculty faculty) {
        return facultyRepository.save(faculty);
    }
    public void delete(long id) {
        facultyRepository.deleteById(id);
    }
}