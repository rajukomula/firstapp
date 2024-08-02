package com.example.backendapp.email;

public interface EmailSender {
    void send(String to, String email);
}