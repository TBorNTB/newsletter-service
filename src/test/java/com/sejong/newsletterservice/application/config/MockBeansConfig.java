package com.sejong.newsletterservice.application.config;

import static org.mockito.Mockito.mock;

import com.sejong.newsletterservice.application.email.NewsletterService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

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
