package it.ai.polito.lab2.service.exceptions;

public class TokenNotFoundException extends NotificationException{
    public TokenNotFoundException(String token) {
        super(String.format("Token <%s> doesn't exist", token));
    }
}
