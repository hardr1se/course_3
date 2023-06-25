package ru.hogwarts.dto.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDtoIn {
    private Long id;
    private String name;
    private int age;
    private long facultyId;
}
