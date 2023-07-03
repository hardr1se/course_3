package ru.hogwarts.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hogwarts.dto.in.StudentDtoIn;
import ru.hogwarts.dto.out.StudentDtoOut;
import ru.hogwarts.exceptions.FacultyNotFoundException;
import ru.hogwarts.model.Student;
import ru.hogwarts.repository.FacultyRepository;

import java.util.Optional;

@AllArgsConstructor
@Component
public class StudentMapper {
    private final FacultyMapper facultyMapper;
    private final FacultyRepository facultyRepository;

    public StudentDtoOut toDto(Student student) {
        StudentDtoOut studentDtoOut = new StudentDtoOut();
        studentDtoOut.setName(student.getName());
        studentDtoOut.setAge(student.getAge());
        Optional.ofNullable(student.getFaculty())
                .ifPresent(faculty -> studentDtoOut.setFaculty(facultyMapper.toDto(faculty)));
        return studentDtoOut;
    }

    public Student toEntity(StudentDtoIn studentDtoIn) {
        Student student = new Student();
        student.setName(studentDtoIn.getName());
        student.setAge(studentDtoIn.getAge());
        Optional.ofNullable(studentDtoIn.getFacultyId())
                .ifPresent(facultyRepository::findById);
        return student;
    }
}
