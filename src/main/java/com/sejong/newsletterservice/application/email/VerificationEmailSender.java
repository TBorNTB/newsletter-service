package com.sejong.newsletterservice.application.email;


public interface VerificationEmailSender {
    void send(String to, String subjectOrCode);
}