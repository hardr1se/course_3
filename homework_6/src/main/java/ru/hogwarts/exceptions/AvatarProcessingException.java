package ru.hogwarts.exceptions;

public class AvatarProcessingException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Error of processing uploaded avatar";
    }
}
