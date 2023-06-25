package ru.hogwarts.mapper;

import org.springframework.stereotype.Component;
import ru.hogwarts.dto.in.FacultyDtoIn;
import ru.hogwarts.dto.out.FacultyDtoOut;
import ru.hogwarts.model.Faculty;

@Component
public class FacultyMapper {
    public FacultyDtoOut toDto(Faculty faculty) {
        FacultyDtoOut facultyDtoOut = new FacultyDtoOut();
        facultyDtoOut.setId(faculty.getId());
        facultyDtoOut.setName(faculty.getName());
        facultyDtoOut.setColour(faculty.getColour());
        return facultyDtoOut;
    }

    public Faculty toEntity(FacultyDtoIn facultyDtoIn) {
        Faculty faculty = new Faculty();
        faculty.setName(facultyDtoIn.getName());
        faculty.setColour(facultyDtoIn.getColour());
        return faculty;
    }
}
