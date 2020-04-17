package it.ai.polito.lab2.exceptions;

public class StudentNotFoundException extends TeamServiceException {
    public StudentNotFoundException() {
        super();
        System.err.println("Student doesn't exist");
    }

    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotFoundException(Throwable cause) {
        super(cause);
    }

    protected StudentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
