package com.userservice.exception;

public class NoSuchUserException extends Exception{
    public NoSuchUserException(String message){
        super(message);
    }
}
