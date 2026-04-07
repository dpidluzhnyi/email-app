package com.notificationservice.exception;

public class FailedToCreateUserException extends Exception{

    public FailedToCreateUserException(String unableToCreateNewUser) {
        super(unableToCreateNewUser);
    }
}
