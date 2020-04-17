package it.ai.polito.lab2.service.exceptions;

public class TeamNotFoundException extends TeamServiceException{
    public TeamNotFoundException() {
        super("Team doesn't exist");
    }
}
