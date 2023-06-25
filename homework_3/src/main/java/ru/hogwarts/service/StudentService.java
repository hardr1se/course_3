package ru.hogwarts.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.dto.out.FacultyDtoOut;
import ru.hogwarts.dto.in.StudentDtoIn;
import ru.hogwarts.dto.out.StudentDtoOut;
import ru.hogwarts.exceptions.StudentNotFoundException;
import ru.hogwarts.mapper.StudentMapper;
import ru.hogwarts.model.Student;
import ru.hogwarts.repository.FacultyRepository;
import ru.hogwarts.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final FacultyRepository facultyRepository;

    public StudentDtoOut create(StudentDtoIn studentDtoIn) {
        return studentMapper.toDto(studentRepository.save(studentMapper.toEntity(studentDtoIn)));
    }

    public StudentDtoOut find(long id) {
        return Optional.ofNullable(studentRepository.findById(id))
                .map(studentMapper::toDto)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Collection<StudentDtoOut> getAll() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public FacultyDtoOut findFacultyOfStudent(long studentId) {
        return Optional.ofNullable(studentRepository.findById(studentId))
                .map(studentMapper::toDto)
                .orElseThrow(() -> new StudentNotFoundException(studentId))
                .getFaculty();
    }

    public StudentDtoOut update(StudentDtoIn student) {
        return studentRepository.findById(student.getId())
                .map(old -> {
                    old.setName(student.getName());
                    old.setAge(student.getAge());
                    old.setFaculty(facultyRepository.findById(student.getFacultyId()));
                    return studentMapper.toDto(studentRepository.save(old));
                })
                .orElseThrow(() -> new StudentNotFoundException(student.getId()));
    }

    public StudentDtoOut delete(long id) {
        Student student = Optional.ofNullable(studentRepository.findById(id))
                .orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.delete(student);
        return studentMapper.toDto(student);
    }

    public Collection<StudentDtoOut> getStudentsByAge(int age) {
        return studentRepository.findByAge(age).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public Collection<StudentDtoOut> findByAgeBetween(int from, int to) {
        return studentRepository.findByAgeBetween(from, to).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
}