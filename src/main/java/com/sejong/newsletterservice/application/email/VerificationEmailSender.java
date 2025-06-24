package com.sejong.newsletterservice.application.email;


public interface EmailSender {
    void send(String to, String subjectOrCode);
}