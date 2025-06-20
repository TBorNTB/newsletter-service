package com.sejong.newsletterservice.api.controller;

import com.sejong.newsletterservice.api.controller.dto.request.EmailRequest;
import com.sejong.newsletterservice.api.controller.dto.request.SubscriberRequest;
import com.sejong.newsletterservice.api.controller.dto.request.VerifyRequest;
import com.sejong.newsletterservice.api.controller.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.application.service.EmailService;
import com.sejong.newsletterservice.application.service.SubscriberService;
import com.sejong.newsletterservice.application.service.VerificationService;
import com.sejong.newsletterservice.domain.model.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.infrastructure.redis.SubscriberCacheService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/newsletter")
public class SubscriberController {

    private final SubscriberService subscriberService;
    private final VerificationService verificationService;

    @PostMapping("/subscribe/verification-code")
    public ResponseEntity<String> subscribeStart(
            @Valid @RequestBody SubscriberRequest subscriberRequest
    ) {
        String code = verificationService.generateCode();

        SubscriberRequestVO subscriberRequestVO = subscriberRequest.toVO( code);
        verificationService.sendVerification(subscriberRequestVO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Verification email sent. Please check your inbox.");
    }

    @PostMapping("/subscribers/verify")
    public ResponseEntity<SubscriberResponse> verifyCode(@RequestBody VerifyRequest request) {
        SubscriberRequestVO subscriberRequestVO = verificationService.verifyEmailCode(request.getEmail(), request.getCode());
        SubscriberResponse response = subscriberService.register(subscriberRequestVO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
