package ru.hogwarts.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.dto.in.FacultyDtoIn;
import ru.hogwarts.dto.out.FacultyDtoOut;
import ru.hogwarts.dto.out.StudentDtoOut;
import ru.hogwarts.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public FacultyDtoOut createFaculty(@RequestBody FacultyDtoIn facultyDtoIn) {
        return facultyService.create(facultyDtoIn);
    }

    @GetMapping("{id}")
    public FacultyDtoOut findFaculty(@PathVariable Long id) {
        return facultyService.find(id);
    }

    @GetMapping
    public Collection<FacultyDtoOut> getFacultiesByColour(@RequestParam(required = false) String colourOrName) {
        return colourOrName != null ?
                facultyService.findFacultiesByColourOrName(colourOrName) :
                facultyService.getAll();
    }

    @GetMapping("/students")
    public Collection<StudentDtoOut> getFacultiesByColour(@RequestParam(required = false) Long facultyId) {
        return facultyService.findStudentsOfFaculty(facultyId);
    }

    @PutMapping
    public FacultyDtoOut updateFaculty(@RequestBody FacultyDtoIn facultyDtoIn) {
        return facultyService.update(facultyDtoIn);
    }

    @DeleteMapping("{id}")
    public FacultyDtoOut deleteFaculty(@PathVariable Long id) {
        return facultyService.delete(id);
    }
}
