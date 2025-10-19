package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.infrastructure.feign.response.MetaVisitersAllResponse;

public interface NewsletterEmailSender {
    void send(String to, String subject, String csKnowledgeId);
    void sendMostVisiters(String email, String title, MetaVisitersAllResponse response);
}
