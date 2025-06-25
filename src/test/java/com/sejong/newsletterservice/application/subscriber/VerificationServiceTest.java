package com.sejong.newsletterservice.application.subscriber;

import com.sejong.newsletterservice.application.email.VerificationEmailSender;
import com.sejong.newsletterservice.infrastructure.redis.SubscriberCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class VerificationServiceTest {

    private VerificationService verificationService;
    @Mock
    private VerificationEmailSender verificationEmailSender;
    @Mock
    private SubscriberCacheService subscriberCacheService;

    @BeforeEach
    void setUp() {
        verificationService = new VerificationService(verificationEmailSender, subscriberCacheService);
    }

    @Test
    void generateCode는_항상_6자리_숫자_문자열을_반환한다() {
        // when
        String code = verificationService.generateCode();

        // then
        assertNotNull(code, "코드는 null이 아니어야 한다.");
        assertEquals(6, code.length(), "코드는 6자리어야 한다.");
        assertTrue(code.matches("\\d{6}"), "코드는 숫자만 포함해야 한다.");

        int numericCode = Integer.parseInt(code);
        assertTrue(numericCode >= 100000 && numericCode <= 999999, "코드의 범위는 100000이상 999999이하");
    }

    @Test
    void generateCode를_여러번_호출해도_형식은_일정하다() {
        for (int i = 0; i < 1000; i++) {
            String code = verificationService.generateCode();
            assertEquals(6, code.length());
            assertTrue(code.matches("\\d{6}"));
        }
    }


}