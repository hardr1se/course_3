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
import ru.hogwarts.controller.FacultyController;
import ru.hogwarts.model.Faculty;
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
public class FacultyControllerTests {
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
    private FacultyController facultyController;

    @Test
    public void createStudentTest() throws Exception {
        final Long id = 1L;
        final String name = "Гриффиндор";
        final String colour = "Синий";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("colour", colour);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColour(colour);

        Mockito.when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.colour").value(colour));
    }

    @Test
    public void findStudentTest() throws Exception {
        final long success = 1;
        final long exception = 2;

        final Long id = 1L;
        final String name = "Гриффиндор";
        final String colour = "Синий";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("colour", colour);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColour(colour);

        Mockito.when(facultyRepository.getFacultyById(success)).thenReturn(faculty);
        Mockito.when(facultyRepository.getFacultyById(exception)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + success)
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.colour").value(colour));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/" + exception))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFacultiesByColourOrNameTest() throws Exception {
        final String name = "Гриффиндор";
        final String colour = "Синий";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("colour", colour);
        Collection<JSONObject> expected = Collections.singleton(facultyObject);

        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColour(colour);

        Mockito.when(facultyRepository.getAllByColourIgnoreCaseOrNameIgnoreCase(any(String.class), any(String.class)))
                .thenReturn(Collections.singleton(faculty));
        Mockito.when(facultyRepository.getFacultyById(any(Long.class)))
                .thenReturn(faculty);
        Mockito.when(facultyRepository.findAll())
                .thenReturn(List.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty?name=Гриффиндор")
                        .content(expected.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].name").value(name))
                .andExpect(jsonPath("[*].colour").value(colour));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty?colour=Гриффиндор")
                        .content(expected.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].name").value(name))
                .andExpect(jsonPath("[*].colour").value(colour));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty?faculty_id=1")
                        .content(expected.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].name").value(name))
                .andExpect(jsonPath("[*].colour").value(colour));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty")
                        .content(expected.stream().toList().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[*].name").value(name))
                .andExpect(jsonPath("[*].colour").value(colour));
    }

    @Test
    public void updateStudentTest() throws Exception {
        final Long id = 1L;
        final String name = "Гриффиндор";
        final String colour = "Синий";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("colour", colour);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColour(colour);

        Mockito.when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.colour").value(colour));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Гриффиндор");
        faculty.setColour("Синий");

        facultyService.create(faculty);

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
