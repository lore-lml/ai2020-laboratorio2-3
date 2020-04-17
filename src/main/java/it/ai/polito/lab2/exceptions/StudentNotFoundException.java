package it.ai.polito.lab2.exceptions;

public class StudentNotFoundException extends TeamServiceException {
    public StudentNotFoundException() {
        super("Student doesn't exist");
    }
}
