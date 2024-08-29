package com.example.backendapp.exceptions;


public class OtpNotSentException extends RuntimeException {
    public OtpNotSentException(String message) {
        super(message);
    }
}