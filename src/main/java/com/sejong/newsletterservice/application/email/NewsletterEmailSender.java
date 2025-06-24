package com.sejong.newsletterservice.application.email;

public interface NewsletterEmailSender {
    void send(String to, String subject, Long csKnowledgeId);
}
