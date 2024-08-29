package com.example.backendapp.exceptions;

public class IncorrectOtpException extends RuntimeException {
    public IncorrectOtpException(String message) {
        super(message);
    }
}