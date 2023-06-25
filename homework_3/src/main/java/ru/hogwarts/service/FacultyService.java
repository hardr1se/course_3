package ru.hogwarts.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.dto.in.FacultyDtoIn;
import ru.hogwarts.dto.out.FacultyDtoOut;
import ru.hogwarts.dto.out.StudentDtoOut;
import ru.hogwarts.exceptions.FacultyNotFoundException;
import ru.hogwarts.mapper.FacultyMapper;
import ru.hogwarts.mapper.StudentMapper;
import ru.hogwarts.model.Faculty;
import ru.hogwarts.repository.FacultyRepository;
import ru.hogwarts.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public FacultyDtoOut create(FacultyDtoIn facultyDtoIn) {
        return facultyMapper.toDto(facultyRepository.save(facultyMapper.toEntity(facultyDtoIn)));
    }

    public FacultyDtoOut find(long id) {
        return Optional.ofNullable(facultyRepository.findById(id))
                .map(facultyMapper::toDto)
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public Collection<FacultyDtoOut> getAll() {
        return facultyRepository.findAll().stream()
                .map(facultyMapper::toDto)
                .collect(Collectors.toList());
    }

    public Collection<FacultyDtoOut> findFacultiesByColourOrName(String colourOrName) {
        return facultyRepository
                .findByColourContainingIgnoreCaseOrNameContainingIgnoreCase(colourOrName, colourOrName)
                .stream()
                .map(facultyMapper::toDto)
                .collect(Collectors.toList());
    }

    public Collection<StudentDtoOut> findStudentsOfFaculty(Long facultyId) {
        return studentRepository.findByFaculty_Id(facultyId).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public FacultyDtoOut update(FacultyDtoIn faculty) {
        return facultyRepository.findById(faculty.getId())
                .map(old -> {
                    old.setName(faculty.getName());
                    old.setColour(faculty.getColour());
                    return facultyMapper.toDto(facultyRepository.save(old));
                })
                .orElseThrow(() -> new FacultyNotFoundException(faculty.getId()));
    }

    public FacultyDtoOut delete(long id) {
        Faculty faculty = Optional.ofNullable(facultyRepository.findById(id))
                .orElseThrow(() -> new FacultyNotFoundException(id));
        facultyRepository.delete(faculty);
        return facultyMapper.toDto(faculty);
    }
}