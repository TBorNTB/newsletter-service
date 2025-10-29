package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.core.subscriber.SubscriberRepository;
import com.sejong.newsletterservice.infrastructure.feign.ElasticServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NewsletterServiceTest {

    private NewsletterService newsletterService;

    @Mock
    private  SubscriberRepository subscriberRepository;
    @Mock
    private NewsletterEmailSender newsletterEmailSender;
    @Mock
    private ElasticServiceClient elasticServiceClient;

    @BeforeEach
    void setUp() {
        newsletterService = new NewsletterService(subscriberRepository, newsletterEmailSender, elasticServiceClient);
    }

}
