package com.sejong.newsletterservice.api.controller;

import com.sejong.newsletterservice.api.controller.dto.request.EmailRequest;
import com.sejong.newsletterservice.api.controller.dto.request.SubscriberRequest;
import com.sejong.newsletterservice.api.controller.dto.request.VerifyRequest;
import com.sejong.newsletterservice.api.controller.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.application.service.EmailService;
import com.sejong.newsletterservice.application.service.SubscriberService;
import com.sejong.newsletterservice.application.service.VerificationService;
import com.sejong.newsletterservice.domain.model.vo.SubscriberRequestVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/newsletter")
public class SubscriberController {

    private final SubscriberService subscriberService;
    private final EmailService emailService;
    private final VerificationService verificationService;

    @PostMapping("/subscribe")
    public ResponseEntity<SubscriberResponse> subscribe(
            @Valid @RequestBody SubscriberRequest subscriberRequest
    ) {
        SubscriberRequestVO subscriberRequestVO = subscriberRequest.toVO();
        SubscriberResponse response = subscriberService.register(subscriberRequestVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/send-code")
    public ResponseEntity<String> sendCode(
            @RequestBody EmailRequest request
    ) {
        String code = verificationService.generateAndStoreCode(request.getEmail());
        emailService.sendVerificationEmail(request.getEmail(), code);
        return ResponseEntity.ok("Verification code sent.");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestBody VerifyRequest request) {
        boolean isValid = verificationService.verifyEmailCode(request.getEmail(), request.getCode());
        if (isValid) {
            return ResponseEntity.ok("Subscription successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid verification code.");
        }
    }

}
