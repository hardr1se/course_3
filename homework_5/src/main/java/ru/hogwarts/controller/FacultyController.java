package ru.hogwarts.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.model.Faculty;
import ru.hogwarts.service.FacultyService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.find(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    public Collection<Faculty> getFacultiesByColourOrName(@RequestParam(required = false) String colour,
                                                          @RequestParam(required = false) String name,
                                                          @RequestParam(required = false) Long faculty_id) {
        if (colour != null) {
            return facultyService.getFacultiesByColourOrName(colour);
        } else if (name != null) {
            return facultyService.getFacultiesByColourOrName(name);
        } else if (faculty_id != null) {
            return Collections.singleton(facultyService.getFacultiesByFaculty_IdOfStudent(faculty_id));
        }
        return facultyService.getAll();
    }

    @PutMapping
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        return facultyService.update(faculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }
}