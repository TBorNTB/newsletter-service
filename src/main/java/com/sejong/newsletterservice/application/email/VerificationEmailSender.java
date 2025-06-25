package com.sejong.newsletterservice.application.email;


public interface VerificationEmailSender {
    String send(String to, String subjectOrCode);
}