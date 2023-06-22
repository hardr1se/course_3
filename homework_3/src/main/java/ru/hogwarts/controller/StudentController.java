package ru.hogwarts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.model.Faculty;
import ru.hogwarts.model.Student;
import ru.hogwarts.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> findStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping("sort/{age}")
    public Collection<Student> getStudentsByAge(@PathVariable int age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping
    public Collection<Student> findByAgeBetween(@RequestParam(required = false) Integer max,
                                                @RequestParam(required = false) Integer min) {
        if (max != null && min != null) {
            return studentService.findByAgeBetween(min, max);
        }
        return studentService.getAllStudents();
    }

    @GetMapping("faculty/{id}")
    public Faculty findByAgeBetween(@PathVariable Integer id) {
        return studentService.getStudentsByFaculty(id).getFaculty();
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
