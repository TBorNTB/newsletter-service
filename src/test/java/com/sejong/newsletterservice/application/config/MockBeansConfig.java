package com.sejong.newsletterservice.application.config;

import com.sejong.newsletterservice.application.email.NewsletterService;
import com.sejong.newsletterservice.application.email.SentLogFlusher;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class MockBeansConfig {


    @Bean
    public NewsletterService newsletterService() {
        return mock(NewsletterService.class);
    }

    @Bean
    public SentLogFlusher sentLogFlusher() {
        return mock(SentLogFlusher.class);
    }
}
