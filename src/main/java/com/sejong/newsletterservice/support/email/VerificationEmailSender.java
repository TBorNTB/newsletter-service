package com.sejong.newsletterservice.support.email;

public interface VerificationEmailSender {
    void send(String to, String code);
}
