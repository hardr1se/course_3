package ru.hogwarts.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.dto.out.FacultyDtoOut;
import ru.hogwarts.dto.in.StudentDtoIn;
import ru.hogwarts.dto.out.StudentDtoOut;
import ru.hogwarts.exceptions.StudentNotFoundException;
import ru.hogwarts.mapper.StudentMapper;
import ru.hogwarts.model.Avatar;
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
    private final AvatarService avatarService;

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

    public StudentDtoOut uploadAvatar(Long id, MultipartFile file) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        Avatar avatar = avatarService.uploadAvatar(id, file);
        StudentDtoOut studentDtoOut = studentMapper.toDto(student);
        studentDtoOut.setAvatarUri("http://localhost:8080/avatars/" + avatar.getId() + "/fs");
        return studentDtoOut;
    }

    public Integer findTotalStudents() {
        return studentRepository.findTotalStudents();
    }

    public Integer findAverageAgeOfStudents() {
        return studentRepository.findAverageAgeOfStudents();
    }

    public Collection<StudentDtoOut> findLastFiveStudents() {
        return studentRepository.findLastFiveStudents().stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
}