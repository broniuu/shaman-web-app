package com.example.shamanApi.exception;

public class PasswordNotCorrectException extends RuntimeException {
    public PasswordNotCorrectException(String message) {
        super(message);
    }
}
