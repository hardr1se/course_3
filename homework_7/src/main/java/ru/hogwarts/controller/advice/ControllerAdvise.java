package ru.hogwarts.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.exceptions.AvatarNotFoundException;
import ru.hogwarts.exceptions.AvatarProcessingException;
import ru.hogwarts.exceptions.FacultyNotFoundException;
import ru.hogwarts.exceptions.StudentNotFoundException;

@ControllerAdvice
public class ControllerAdvise {
    @ExceptionHandler({
            AvatarNotFoundException.class,
            FacultyNotFoundException.class,
            StudentNotFoundException.class
    })
    public ResponseEntity<String> notFoundExceptionHandler(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(AvatarProcessingException.class)
    public ResponseEntity<String> processingExceptionHandler(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(e.getMessage());
    }

}
