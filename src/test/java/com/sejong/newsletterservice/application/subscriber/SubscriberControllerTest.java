package com.sejong.newsletterservice.application.subscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sejong.newsletterservice.application.subscriber.dto.request.SubscriptionRequest;
import com.sejong.newsletterservice.application.subscriber.dto.request.VerifyRequest;
import com.sejong.newsletterservice.application.subscriber.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.application.subscriber.dto.response.VerificationResponse;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.fixture.SubscriberRequestFixture;
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
        String fakeCode = "123456";
        SubscriberRequestVO requestVO = subscriptionRequest.toVO(fakeCode);
        VerificationResponse verificationResponse = new VerificationResponse("test@email.com","이메일이 성공적으로 전송되었습니다.");

        when(verificationService.generateCode()).thenReturn(fakeCode);
        when(verificationService.sendVerification(requestVO)).thenReturn(verificationResponse);

        // when
        ResponseEntity<VerificationResponse> response = subscriberController.subscribeStart(subscriptionRequest);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().getEmail()).isEqualTo("test@email.com");
        assertThat(response.getBody().getMessage()).isEqualTo("이메일이 성공적으로 전송되었습니다.");

        verify(verificationService).generateCode();
        verify(verificationService).sendVerification(requestVO);
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


}