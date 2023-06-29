package ru.hogwarts;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.controller.StudentController;
import ru.hogwarts.model.Student;
import ru.hogwarts.repository.AvatarRepository;
import ru.hogwarts.repository.FacultyRepository;
import ru.hogwarts.repository.StudentRepository;
import ru.hogwarts.service.AvatarService;
import ru.hogwarts.service.FacultyService;
import ru.hogwarts.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class StudentControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private StudentService studentService;
    @SpyBean
    private FacultyService facultyService;
    @SpyBean
    private AvatarService avatarService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void createStudentTest() throws Exception {
        final Long id = 1L;
        final String name = "Виталий";
        final int age = 33;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        Mockito.when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void findStudentTest() throws Exception {
        final long success = 1;
        final long exception = 2;

        final Long id = 1L;
        final String name = "Виталий";
        final int age = 33;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        Mockito.when(studentRepository.findById(success)).thenReturn(student);
        Mockito.when(studentRepository.findById(exception)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/" + success)
                .content(studentObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/" + exception))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getStudentsByAgeTest() throws Exception {
        final String name = "Виталий";
        final int age = 33;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Collection<JSONObject> jsonExpected = Collections.singleton(studentObject);

        Student student = new Student();
        student.setName(name);
        student.setAge(age);

        Collection<Student> expected = Collections.singleton(student);

        Mockito.when(studentRepository.getAllByAge(any(int.class))).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/sort/1")
                .content(jsonExpected.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].name").value(name))
                .andExpect(jsonPath("[*].age").value(age));
    }

    @Test
    public void findByAgeBetweenTest() throws Exception {
        final String name = "Виталий";
        final int age = 33;

        Student student = new Student();
        student.setName(name);
        student.setAge(age);

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);
        Collection<JSONObject> expected = Collections.singleton(studentObject);

        Mockito.when(studentRepository.findByAgeBetween(any(int.class), any(int.class)))
                .thenReturn(Collections.singleton(student));
        Mockito.when(studentRepository.findByFaculty_Id(any(Long.class)))
                .thenReturn(Collections.singleton(student));
        Mockito.when(studentRepository.findAll())
                .thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student?max=20&min=22")
                        .content(expected.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].name").value(name))
                .andExpect(jsonPath("[*].age").value(age));

        mockMvc.perform(MockMvcRequestBuilders.get("/student?facultyId=1")
                .content(expected.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].name").value(name))
                .andExpect(jsonPath("[*].age").value(age));

        mockMvc.perform(MockMvcRequestBuilders.get("/student")
                .content(expected.stream().toList().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].name").value(name))
                .andExpect(jsonPath("[*].age").value(age));
    }

    @Test
    public void updateStudentTest() throws Exception {
        final Long id = 1L;
        final String name = "Виталий";
        final int age = 33;

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        Mockito.when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Виталий");
        student.setAge(33);

        studentService.create(student);

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
