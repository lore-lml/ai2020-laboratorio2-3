package it.ai.polito.lab2.exceptions;

public class CourseNotFoundException extends TeamServiceException{
    public CourseNotFoundException() {
        super("Course doesn't exist");
    }
}
