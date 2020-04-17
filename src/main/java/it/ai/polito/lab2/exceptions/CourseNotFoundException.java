package it.ai.polito.lab2.exceptions;

public class CourseNotFoundException extends TeamServiceException{
    public CourseNotFoundException() {
        super();
        System.err.println("Course doesn't exist");
    }

    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CourseNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CourseNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
