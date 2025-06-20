package com.sejong.newsletterservice.application.service;

import com.sejong.newsletterservice.domain.model.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.infrastructure.redis.SubscriberCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerificationService {
    private final EmailService emailService;
    private final SubscriberCacheService subscriberCacheService;

    public String generateCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public void sendVerification(SubscriberRequestVO subscriberRequestVO) {

        subscriberCacheService.save(subscriberRequestVO);
        emailService.sendVerificationEmail(subscriberRequestVO.email(), subscriberRequestVO.code());
    }

    public SubscriberRequestVO verifyEmailCode(String email, String inputCode) {
        return subscriberCacheService.getEmailInfo(email)
                .orElseThrow(() -> new IllegalStateException("인증 정보 없음: " + email));
    }
}
