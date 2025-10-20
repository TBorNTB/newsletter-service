package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.infrastructure.feign.response.ContentResponse;
import java.util.List;

public interface NewsletterEmailSender {
    void send(String to, String subject, String csKnowledgeId);
    void sendPopularContent(String email, String title, ContentResponse response);
    void sendInterestingCategoryContents(String email, String title, List<ContentResponse> responses);
}
