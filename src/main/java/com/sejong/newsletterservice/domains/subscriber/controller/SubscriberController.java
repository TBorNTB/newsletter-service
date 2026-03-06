package com.sejong.newsletterservice.domains.subscriber.controller;

import com.sejong.newsletterservice.domains.subscriber.dto.request.EmailRequest;
import com.sejong.newsletterservice.domains.subscriber.dto.request.SubscriberRequestVO;
import com.sejong.newsletterservice.domains.subscriber.dto.request.SubscriptionRequest;
import com.sejong.newsletterservice.domains.subscriber.dto.request.UpdateSubscriptionRequest;
import com.sejong.newsletterservice.domains.subscriber.dto.request.VerifyRequest;
import com.sejong.newsletterservice.domains.subscriber.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.domains.subscriber.dto.response.SubscriberStatusResponse;
import com.sejong.newsletterservice.domains.subscriber.dto.response.VerificationResponse;
import com.sejong.newsletterservice.domains.subscriber.service.SubscriberService;
import com.sejong.newsletterservice.domains.subscriber.service.VerificationService;
import com.sejong.newsletterservice.support.exception.ApiException;
import com.sejong.newsletterservice.support.exception.ErrorCode;
import com.sejong.newsletterservice.support.util.RandomProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/newsletter")
@Tag(name = "Newsletter Subscription", description = "뉴스레터 구독 관련 API")
public class SubscriberController {

    private final SubscriberService subscriberService;
    private final VerificationService verificationService;

    @Operation(summary = "헬스 체크")
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @Operation(summary = "뉴스레터 구독 인증 코드 발송")
    @PostMapping("/subscribe/verification-code")
    public ResponseEntity<VerificationResponse> subscribeStart(
            @Valid @RequestBody SubscriptionRequest request
    ) {
        String code = RandomProvider.generateRandomCode(6);
        VerificationResponse response = verificationService.sendVerification(request.toVO(code));
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "구독 인증 코드 검증 및 구독 완료")
    @PostMapping("/subscribers/verify")
    public ResponseEntity<SubscriberResponse> verifyCode(@RequestBody VerifyRequest request) {
        SubscriberRequestVO vo = verificationService.verifyEmailCode(request.getEmail(), request.getCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriberService.register(vo));
    }

    @Operation(summary = "단순 이메일 인증")
    @PostMapping("/subscribers/verify/email")
    public ResponseEntity<SubscriberResponse> verifyEmailOnly(@RequestBody VerifyRequest request) {
        verificationService.verifyCancelEmailCode(request.getEmail(), request.getCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(SubscriberResponse.verified(request.getEmail()));
    }

    @Operation(summary = "구독 해제 인증 코드 발송")
    @PostMapping("/subscribers/verify/cancel")
    public ResponseEntity<VerificationResponse> subscriberCancelStart(
            @Valid @RequestBody EmailRequest emailRequest
    ) {
        String code = RandomProvider.generateRandomCode(6);
        return ResponseEntity.ok(verificationService.sendCancelVerification(emailRequest, code));
    }

    @Operation(summary = "인증 코드 검증 후 구독 해제")
    @PatchMapping("/subscribers/verify/cancel")
    public ResponseEntity<SubscriberResponse> cancelVerifyCode(@RequestBody VerifyRequest request) {
        String email = verificationService.verifyCancelEmailCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(subscriberService.deleteSubscriber(email));
    }

    @Operation(summary = "구독 설정 변경 인증 코드 발송")
    @PostMapping("/subscribers/preferences/verification-code")
    public ResponseEntity<VerificationResponse> updatePreferencesStart(
            @Valid @RequestBody UpdateSubscriptionRequest request
    ) {
        String code = RandomProvider.generateRandomCode(6);
        return ResponseEntity.ok(verificationService.sendUpdateVerification(request.toVO(code)));
    }

    @Operation(summary = "구독 설정 변경(인증 후)")
    @PatchMapping("/subscribers/preferences/verify")
    public ResponseEntity<SubscriberResponse> updatePreferencesVerified(
            @Valid @RequestBody VerifyRequest request
    ) {
        SubscriberRequestVO vo = verificationService.verifyUpdateEmailCode(request.getEmail(), request.getCode());
        if (!vo.email().equals(request.getEmail())) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "이메일 정보 불일치");
        }
        return ResponseEntity.ok(subscriberService.updatePreferences(vo));
    }

    @Operation(summary = "구독 상태 조회")
    @GetMapping("/subscribers/status")
    public ResponseEntity<SubscriberStatusResponse> getSubscriberStatus(@RequestParam("email") String email) {
        return ResponseEntity.ok(subscriberService.getStatus(email));
    }
}
