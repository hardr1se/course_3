package ru.hogwarts.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.dto.in.StudentDtoIn;
import ru.hogwarts.dto.out.FacultyDtoOut;
import ru.hogwarts.dto.out.StudentDtoOut;
import ru.hogwarts.exceptions.AvatarProcessingException;
import ru.hogwarts.service.AvatarService;
import ru.hogwarts.service.StudentService;

import java.io.IOException;
import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping("student")
public class StudentController {
    StudentService studentService;
    AvatarService avatarService;

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

    @GetMapping("total")
    public Integer findTotalStudents() {
        return studentService.findTotalStudents();
    }

    @GetMapping("average-age")
    public Integer findAverageAgeOfStudents() {
        return studentService.findAverageAgeOfStudents();
    }

    @GetMapping("last-five")
    public Collection<StudentDtoOut> findLastFiveStudents() {
        return studentService.findLastFiveStudents();
    }

    @PutMapping
    public StudentDtoOut updateStudent(@RequestBody StudentDtoIn studentDtoIn) {
        return studentService.update(studentDtoIn);
    }

    @DeleteMapping("{id}")
    public StudentDtoOut deleteStudent(@PathVariable Long id) {
        return studentService.delete(id);
    }

    @PatchMapping(value = "{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StudentDtoOut uploadAvatar(@PathVariable Long id, @RequestPart MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 300) {
            throw new AvatarProcessingException();
        }
        return studentService.uploadAvatar(id, avatar);
    }
}
