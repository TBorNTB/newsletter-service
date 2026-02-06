package com.sejong.newsletterservice.application.subscriber;

import com.sejong.newsletterservice.application.subscriber.dto.request.EmailRequest;
import com.sejong.newsletterservice.application.subscriber.dto.request.SubscriptionRequest;
import com.sejong.newsletterservice.application.subscriber.dto.request.UpdateSubscriptionRequest;
import com.sejong.newsletterservice.application.subscriber.dto.request.VerifyRequest;
import com.sejong.newsletterservice.application.subscriber.dto.response.SubscriberCancelResponse;
import com.sejong.newsletterservice.application.subscriber.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.application.subscriber.dto.response.SubscriberStatusResponse;
import com.sejong.newsletterservice.application.subscriber.dto.response.VerificationResponse;
import com.sejong.newsletterservice.core.error.code.ErrorCode;
import com.sejong.newsletterservice.core.error.exception.ApiException;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.core.util.RandomProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @Operation(summary = "단순 이메일 인증 api", description = "단순 이메일 인증 api")
    @PostMapping("/subscribers/verify/email")
    public ResponseEntity<SubscriberResponse> verifyCodeOnlyEmail(@RequestBody VerifyRequest request) {
        String email = verificationService.verifyCancelEmailCode(request.getEmail(), request.getCode());
        SubscriberResponse response = SubscriberResponse.from(email);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "구독 해제 이메일 인증코드 발송", description = "구독 해제시 이메일 인증을 위한 api")
    @PostMapping("/subscribers/verify/cancel")
    public ResponseEntity<VerificationResponse> subscriberCancelStart(
            @RequestBody @Valid EmailRequest emailRequest
    ) {
        String code = RandomProvider.generateRandomCode(6);
        VerificationResponse verificationResponse = verificationService.sendVerification(emailRequest, code);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(verificationResponse);
    }

    @Operation(summary = "코드 검증 후 구독 해제 SoftDelete", description = "인증 코드 검증 후 구독 해제 api")
    @PatchMapping("/subscribers/verify/cancel")
    public ResponseEntity<SubscriberResponse> cancelVerifyCode(
            @RequestBody VerifyRequest request
    ){
        String email = verificationService.verifyCancelEmailCode(request.getEmail(), request.getCode());
        SubscriberResponse response = subscriberService.deleteSubscriber(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

        @Operation(summary = "구독 설정 변경 인증 코드 발송", description = "구독 설정 변경을 위한 이메일 인증 코드를 발송합니다.")
        @PostMapping("/subscribers/preferences/verification-code")
        public ResponseEntity<VerificationResponse> updatePreferencesStart(
                        @RequestBody @Valid UpdateSubscriptionRequest request
        ) {
                String code = RandomProvider.generateRandomCode(6);
                SubscriberRequestVO vo = request.toVO(code);
                VerificationResponse verificationResponse = verificationService.sendUpdateVerification(vo);
                return ResponseEntity.status(HttpStatus.OK).body(verificationResponse);
        }

        @Operation(summary = "구독 설정 변경(인증 후)", description = "이메일 인증 코드를 검증한 뒤 카테고리/발송주기(Daily/Weekly) 설정을 변경합니다.")
        @PatchMapping("/subscribers/preferences/verify")
        public ResponseEntity<SubscriberResponse> updatePreferencesVerified(
                        @Valid @RequestBody VerifyRequest request
        ) {
                SubscriberRequestVO verifiedVO = verificationService.verifyUpdateEmailCode(request.getEmail(), request.getCode());
                if (!verifiedVO.email().equals(request.getEmail())) {
                        throw new ApiException(ErrorCode.BAD_REQUEST, "이메일 정보 불일치");
                }
                SubscriberResponse response = subscriberService.updatePreferences(verifiedVO);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        @Operation(summary = "구독 상태 조회", description = "특정 이메일이 등록되어있는지/해제되었는지와 현재 설정(주기/카테고리)을 조회합니다.")
        @GetMapping("/subscribers/status")
        public ResponseEntity<SubscriberStatusResponse> getSubscriberStatus(@RequestParam("email") String email) {
                SubscriberStatusResponse response = subscriberService.getStatus(email);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }


}
