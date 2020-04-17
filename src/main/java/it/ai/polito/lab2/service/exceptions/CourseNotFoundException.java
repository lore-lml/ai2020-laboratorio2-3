package it.ai.polito.lab2.service.exceptions;

public class CourseNotFoundException extends TeamServiceException{
    public CourseNotFoundException() {
        super("Course doesn't exist");
    }
}
