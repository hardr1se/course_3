package ru.hogwarts.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.dto.in.StudentDtoIn;
import ru.hogwarts.dto.out.FacultyDtoOut;
import ru.hogwarts.dto.out.StudentDtoOut;
import ru.hogwarts.service.StudentService;

import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping("student")
public class StudentController {
    StudentService studentService;

    @PostMapping
    public StudentDtoOut createStudent(@RequestBody StudentDtoIn studentDtoIn) {
        return studentService.create(studentDtoIn);
    }

    @GetMapping("{id}")
    public StudentDtoOut findStudent(@PathVariable Long id) {
        return studentService.find(id);
    }

    @GetMapping
    public Collection<StudentDtoOut> findByAgeBetween(@RequestParam(required = false) Integer from,
                                                      @RequestParam(required = false) Integer to,
                                                      @RequestParam(required = false) Integer age) {
        if (from != null && to != null) {
            return studentService.findByAgeBetween(to, from);
        } else if (age != null) {
            return studentService.getStudentsByAge(age);
        }
        return studentService.getAll();
    }

    @GetMapping("faculty/{id}")
    public FacultyDtoOut findFacultyOfStudent(@PathVariable Long id) {
        return studentService.findFacultyOfStudent(id);
    }

    @PutMapping
    public StudentDtoOut updateStudent(@RequestBody StudentDtoIn studentDtoIn) {
        return studentService.update(studentDtoIn);
    }

    @DeleteMapping("{id}")
    public StudentDtoOut deleteStudent(@PathVariable Long id) {
        return studentService.delete(id);
    }
}
