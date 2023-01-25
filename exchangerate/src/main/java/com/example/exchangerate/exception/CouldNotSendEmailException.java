package com.example.exchangerate.exception;

public class CouldNotSendEmailException extends RuntimeException {
    public CouldNotSendEmailException(String message) {
        super(message);
    }
}
