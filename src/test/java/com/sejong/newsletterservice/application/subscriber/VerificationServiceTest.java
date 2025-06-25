package com.sejong.newsletterservice.application.subscriber;

import com.sejong.newsletterservice.application.email.VerificationEmailSender;
import com.sejong.newsletterservice.infrastructure.redis.SubscriberCacheService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class VerificationServiceTest {

    private VerificationService verificationService;
    @Mock
    private  VerificationEmailSender verificationEmailSender;
    @Mock
    private  SubscriberCacheService subscriberCacheService;

}