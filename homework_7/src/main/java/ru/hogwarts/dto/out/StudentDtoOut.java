package ru.hogwarts.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDtoOut {
    private Long id;
    private String name;
    private int age;
    private FacultyDtoOut faculty;
    private String avatarUri;
}
