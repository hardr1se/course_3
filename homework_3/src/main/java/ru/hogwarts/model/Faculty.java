package ru.hogwarts.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "faculty")
public class Faculty {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String colour;

    @OneToMany(mappedBy = "faculty")
    private Collection<Student> students;
}