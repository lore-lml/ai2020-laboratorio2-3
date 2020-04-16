package it.ai.polito.lab2.exceptions;

public class TeamServiceException extends RuntimeException {
    public TeamServiceException() {
        super();
    }

    public TeamServiceException(String message) {
        super(message);
    }

    public TeamServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamServiceException(Throwable cause) {
        super(cause);
    }

    protected TeamServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
