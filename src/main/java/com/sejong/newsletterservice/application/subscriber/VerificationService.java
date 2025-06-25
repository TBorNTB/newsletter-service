package com.sejong.newsletterservice.application.subscriber;

import com.sejong.newsletterservice.application.email.VerificationEmailSender;
import com.sejong.newsletterservice.application.subscriber.dto.response.VerificationResponse;
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

    public VerificationResponse sendVerification(SubscriberRequestVO subscriberRequestVO) {

        subscriberCacheService.save(subscriberRequestVO);
        String email = verificationEmailSender.send(subscriberRequestVO.email(), subscriberRequestVO.code());
        return VerificationResponse.of(email,"이메일이 성공적으로 전송되었습니다.");
    }

    public SubscriberRequestVO verifyEmailCode(String email, String inputCode) {
        return subscriberCacheService.getEmailInfo(email)
                .filter(info -> info.code().equals(inputCode))
                .orElseThrow(() -> new IllegalArgumentException("인증 정보 없음 또는 코드 불일치: " + email));
    }
}
