package com.sejong.newsletterservice.application.subscriber;

import com.sejong.newsletterservice.application.email.VerificationEmailSender;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.infrastructure.redis.SubscriberCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationEmailSender verificationEmailSender;
    private final SubscriberCacheService subscriberCacheService;

    public String generateCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public void sendVerification(SubscriberRequestVO subscriberRequestVO) {

        subscriberCacheService.save(subscriberRequestVO);
        verificationEmailSender.send(subscriberRequestVO.email(), subscriberRequestVO.code());
    }

    public SubscriberRequestVO verifyEmailCode(String email, String inputCode) {
        return subscriberCacheService.getEmailInfo(email)
                .orElseThrow(() -> new IllegalArgumentException("인증 정보 없음: " + email));
    }
}
