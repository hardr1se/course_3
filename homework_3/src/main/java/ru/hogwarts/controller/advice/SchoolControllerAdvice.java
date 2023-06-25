package ru.hogwarts.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.exceptions.FacultyNotFoundException;
import ru.hogwarts.exceptions.StudentNotFoundException;

@ControllerAdvice
public class SchoolControllerAdvice {
    @ExceptionHandler({StudentNotFoundException.class, FacultyNotFoundException.class})
    public ResponseEntity<?> notFoundExceptionHandler(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
