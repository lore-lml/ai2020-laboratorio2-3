package it.ai.polito.lab2.service.exceptions;

public class StudentNotFoundException extends TeamServiceException {
    public StudentNotFoundException() {
        super("Student doesn't exist");
    }
}
