package com.sejong.newsletterservice.application.subscriber;

import com.sejong.newsletterservice.application.subscriber.dto.request.SubscriptionRequest;
import com.sejong.newsletterservice.application.subscriber.dto.request.VerifyRequest;
import com.sejong.newsletterservice.application.subscriber.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.application.subscriber.dto.response.VerificationResponse;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.core.util.RandomProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/newsletter")
@Tag(name = "Newsletter Subscription", description = "뉴스레터 구독 관련 API")
public class SubscriberController {

    private final SubscriberService subscriberService;
    private final VerificationService verificationService;

    @Operation(summary = "헬스 체크", description = "서비스 상태를 확인합니다.")
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Operation(summary = "뉴스레터 구독 인증 코드 발송", description = "이메일로 구독 인증 코드를 발송합니다.")
    @PostMapping("/subscribe/verification-code")
    public ResponseEntity<VerificationResponse> subscribeStart(
            @Valid @RequestBody SubscriptionRequest subscriptionRequest
    ) {
        String code = RandomProvider.generateRandomCode(6);
        SubscriberRequestVO subscriberRequestVO = subscriptionRequest.toVO(code);
        VerificationResponse verificationResponse = verificationService.sendVerification(subscriberRequestVO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(verificationResponse);
    }

    @Operation(summary = "구독 인증 코드 검증", description = "이메일 인증 코드를 검증하고 구독을 완료합니다.")
    @PostMapping("/subscribers/verify")
    public ResponseEntity<SubscriberResponse> verifyCode(@RequestBody VerifyRequest request) {
        SubscriberRequestVO subscriberRequestVO = verificationService.verifyEmailCode(request.getEmail(), request.getCode());
        SubscriberResponse response = subscriberService.register(subscriberRequestVO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

}
