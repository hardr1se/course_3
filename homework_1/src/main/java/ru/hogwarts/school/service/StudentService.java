package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

@Service
public class StudentService {
    private HashMap<Long, Student> students = new HashMap<>();
    private long id = 0;

    public Student createStudent(Student student) {
        student.setId(++id);
        students.put(id, student);
        return student;
    }

    public Student findStudent(long id) {
        return students.get(id);
    }

    public Collection<Student> getAllStudents() {
        return Collections.unmodifiableCollection(students.values());
    }

    public Collection<Student> getStudentsByAge(Long age) {
        return students.values().stream()
                .filter(x -> x.getAge() == age)
                .toList();
    }

    public Student updateStudent(Student student) {
        return students.put(student.getId(), student);
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }
}
