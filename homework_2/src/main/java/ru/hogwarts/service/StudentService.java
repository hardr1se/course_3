package ru.hogwarts.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.model.Student;
import ru.hogwarts.repository.StudentRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Collection<Student> getAllStudents() {
        return Collections.unmodifiableCollection(studentRepository.findAll());
    }

    public Collection<Student> getStudentsByAge(Long age) {
        return studentRepository.findAll().stream()
                .filter(x -> x.getAge() == age)
                .toList();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }
}