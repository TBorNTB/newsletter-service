package com.sejong.newsletterservice.application.service;

import com.sejong.newsletterservice.application.email.EmailSender;
import com.sejong.newsletterservice.domain.model.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.infrastructure.email.EmailVerificationService;
import com.sejong.newsletterservice.infrastructure.redis.SubscriberCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VerificationService {

    private final EmailSender emailSender;
    private final SubscriberCacheService subscriberCacheService;

    public VerificationService(
            @Qualifier("verificationSender") EmailSender emailSender,
            SubscriberCacheService subscriberCacheService
    ) {
        this.emailSender = emailSender;
        this.subscriberCacheService = subscriberCacheService;
    }

    public String generateCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public void sendVerification(SubscriberRequestVO subscriberRequestVO) {

        subscriberCacheService.save(subscriberRequestVO);
        emailSender.send(subscriberRequestVO.email(), subscriberRequestVO.code());
    }

    public SubscriberRequestVO verifyEmailCode(String email, String inputCode) {
        return subscriberCacheService.getEmailInfo(email)
                .orElseThrow(() -> new IllegalStateException("인증 정보 없음: " + email));
    }
}
