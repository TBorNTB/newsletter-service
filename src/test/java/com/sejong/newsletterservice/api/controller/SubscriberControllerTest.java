package com.sejong.newsletterservice.api.controller;

import com.sejong.newsletterservice.application.subscriber.dto.request.SubscriberRequest;
import com.sejong.newsletterservice.application.subscriber.SubscriberController;
import com.sejong.newsletterservice.application.subscriber.SubscriberService;
import com.sejong.newsletterservice.application.subscriber.VerificationService;
import com.sejong.newsletterservice.fixture.SubscriberRequestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class SubscriberControllerTest {

    @Mock
    private SubscriberService subscriberService;
    @Mock
    private VerificationService verificationService;

    @InjectMocks
    private SubscriberController subscriberController;

    private SubscriberRequest request;

    @BeforeEach
    void setUp() {
        request = SubscriberRequestFixture.기본_요청();
    }

    @Test
    void 구독_요청을_정상적으로_처리한다() {
        // when
        ResponseEntity<String> response = subscriberController.subscribeStart(request);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo("Verification email sent. Please check your inbox.");
    }
}