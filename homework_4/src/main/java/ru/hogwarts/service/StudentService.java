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
    public Student create(Student student) {
        return studentRepository.save(student);
    }
    public Student find(long id) {
        return studentRepository.findById(id);
    }
    public Collection<Student> getAll() {
        return Collections.unmodifiableCollection(studentRepository.findAll());
    }

    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.getAllByAge(age);
    }

    public Collection<Student> getStudentsByFaculty(long facultyId) {
        return studentRepository.findByFaculty_Id(facultyId);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Student update(Student student) {
        return studentRepository.save(student);
    }
    public void delete(long id) {
        studentRepository.deleteById(id);
    }
}