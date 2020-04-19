package it.ai.polito.lab2.service.exceptions;

import it.ai.polito.lab2.dtos.CourseDTO;

public class TeamSizeOutOfBoundException extends TeamServiceException {
    public TeamSizeOutOfBoundException(int min, int max) {
        super(String.format("Team size must be between %d and %d", min, max));
    }
}
