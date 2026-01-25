package com.sejong.newsletterservice.application.subscriber;

import com.sejong.newsletterservice.application.email.VerificationEmailSender;
import com.sejong.newsletterservice.application.subscriber.dto.request.EmailRequest;
import com.sejong.newsletterservice.application.subscriber.dto.response.VerificationResponse;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.infrastructure.redis.SubscriberCacheService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationEmailSender verificationEmailSender;
    private final SubscriberCacheService subscriberCacheService;

    public VerificationResponse sendVerification(SubscriberRequestVO subscriberRequestVO) {
        subscriberCacheService.save(subscriberRequestVO);
        verificationEmailSender.send(subscriberRequestVO.email(), subscriberRequestVO.code());
        return VerificationResponse.of(subscriberRequestVO.email(), "인증코드가 이메일로 전송되었습니다.");
    }

    public VerificationResponse sendVerification(EmailRequest emailRequest, String code) {
        subscriberCacheService.save(emailRequest.getEmail(), code);
        verificationEmailSender.send(emailRequest.getEmail(), code);
        return VerificationResponse.of(emailRequest.getEmail(), "인증코드가 이메일로 전송되었습니다.");
    }

    public SubscriberRequestVO verifyEmailCode(String email, String inputCode) {
        return subscriberCacheService.getEmailInfo(email)
                .filter(info -> info.code().equals(inputCode))
                .orElseThrow(() -> new IllegalArgumentException("인증 정보 없음 또는 코드 불일치: " + email));
    }


    public String verifyCancelEmailCode(String email, String inputCode) {
        subscriberCacheService.getEmailCode(email)
                .filter(code -> code.equals(inputCode))
                .orElseThrow(() -> new IllegalArgumentException("인증 정보 없음 또는 코드 불일치: " + email));

        return email;
    }

    public VerificationResponse sendUpdateVerification(EmailRequest emailRequest, String code) {
        subscriberCacheService.saveUpdateCode(emailRequest.getEmail(), code);
        verificationEmailSender.send(emailRequest.getEmail(), code);
        return VerificationResponse.of(emailRequest.getEmail(), "인증코드가 이메일로 전송되었습니다.");
    }

    public String verifyUpdateEmailCode(String email, String inputCode) {
        subscriberCacheService.getUpdateCode(email)
                .filter(code -> code.equals(inputCode))
                .orElseThrow(() -> new IllegalArgumentException("인증 정보 없음 또는 코드 불일치: " + email));

        subscriberCacheService.removeUpdateCode(email);
        return email;
    }
}
