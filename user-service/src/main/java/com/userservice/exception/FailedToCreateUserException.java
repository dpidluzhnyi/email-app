package com.userservice.exception;

public class FailedToCreateUserException extends Exception{

    public FailedToCreateUserException(String message) {
        super(message);
    }
}
