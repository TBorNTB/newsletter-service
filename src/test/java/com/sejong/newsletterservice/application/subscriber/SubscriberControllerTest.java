package com.sejong.newsletterservice.application.subscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sejong.newsletterservice.application.subscriber.dto.request.SubscriptionRequest;
import com.sejong.newsletterservice.application.subscriber.dto.request.UpdatePreferencesVerifyRequest;
import com.sejong.newsletterservice.application.subscriber.dto.request.VerifyRequest;
import com.sejong.newsletterservice.application.subscriber.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.application.subscriber.dto.response.SubscriberStatusResponse;
import com.sejong.newsletterservice.application.subscriber.dto.response.VerificationResponse;
import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.enums.TechCategory;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.fixture.SubscriberRequestFixture;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
class SubscriberControllerTest {

    @Mock
    private SubscriberService subscriberService;
    @Mock
    private VerificationService verificationService;

    @InjectMocks
    private SubscriberController subscriberController;

    @Test
    void 구독_요청시_인증메일을_전송한다() {
        // given
        SubscriptionRequest subscriptionRequest = SubscriberRequestFixture.기본_요청();
        VerificationResponse verificationResponse = VerificationResponse.of("test@email.com","인증코드가 이메일로 전송되었습니다.");

        when(verificationService.sendVerification(any(SubscriberRequestVO.class))).thenReturn(verificationResponse);

        // when
        ResponseEntity<VerificationResponse> response = subscriberController.subscribeStart(subscriptionRequest);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().getEmail()).isEqualTo("test@email.com");
        assertThat(response.getBody().getMessage()).isEqualTo("인증코드가 이메일로 전송되었습니다.");

        verify(verificationService).sendVerification(any(SubscriberRequestVO.class));
    }

    @Test
    void 인증코드가_일치하면_구독을_완료한다() {
        // given
        VerifyRequest verifyRequest = SubscriberRequestFixture.verifyRequest();
        SubscriberRequestVO requestVO = SubscriberRequestFixture.기본_요청_vo();
        SubscriberResponse response =  SubscriberRequestFixture.subscriberResponse();

        when(verificationService.verifyEmailCode(verifyRequest.getEmail(),verifyRequest.getCode())).thenReturn(requestVO);
        when(subscriberService.register(requestVO)).thenReturn(response);

        // when
        ResponseEntity<SubscriberResponse> responseEntity = subscriberController.verifyCode(verifyRequest);
        SubscriberResponse body = responseEntity.getBody();

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(body).isNotNull();
        assertThat(body.getEmail()).isEqualTo("user@example.com");

        verify(verificationService).verifyEmailCode(verifyRequest.getEmail(),verifyRequest.getCode());
        verify(subscriberService).register(requestVO);
    }

    @Test
    void 구독_설정을_변경한다() {
        // given
        UpdatePreferencesVerifyRequest request = new UpdatePreferencesVerifyRequest(
            "user@example.com",
            "123456",
            EmailFrequency.WEEKLY,
            List.of(TechCategory.CRYPTOGRAPHY)
        );
        SubscriberResponse response = SubscriberResponse.builder()
                .email("user@example.com")
                .message("구독 설정이 변경되었습니다.")
                .build();

        when(verificationService.verifyUpdateEmailCode(request.getEmail(), request.getCode())).thenReturn("user@example.com");
        when(subscriberService.updatePreferences(request.toUpdateRequest())).thenReturn(response);

        // when
        ResponseEntity<SubscriberResponse> responseEntity = subscriberController.updatePreferencesVerified(request);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getMessage()).isEqualTo("구독 설정이 변경되었습니다.");

        verify(verificationService).verifyUpdateEmailCode(request.getEmail(), request.getCode());
        verify(subscriberService).updatePreferences(request.toUpdateRequest());
    }

    @Test
    void 이메일로_구독_상태를_조회한다() {
        // given
        SubscriberStatusResponse response = SubscriberStatusResponse.builder()
            .email("user@example.com")
            .registered(true)
            .active(true)
            .emailFrequency(EmailFrequency.DAILY)
            .selectedCategories(List.of(TechCategory.CRYPTOGRAPHY))
            .message("구독중")
            .build();

        when(subscriberService.getStatus("user@example.com")).thenReturn(response);

        // when
        ResponseEntity<SubscriberStatusResponse> responseEntity = subscriberController.getSubscriberStatus("user@example.com");

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().isRegistered()).isTrue();
        assertThat(responseEntity.getBody().getMessage()).isEqualTo("구독중");

        verify(subscriberService).getStatus("user@example.com");
    }


}