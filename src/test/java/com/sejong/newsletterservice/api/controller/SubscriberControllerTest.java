package com.sejong.newsletterservice.api.controller;

import com.sejong.newsletterservice.api.controller.dto.request.SubscriberRequest;
import com.sejong.newsletterservice.api.controller.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.application.service.SubscriberService;
import com.sejong.newsletterservice.fixture.SubscriberRequestFixture;
import com.sejong.newsletterservice.fixture.SubscriberResponseFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SubscriberControllerTest {

    @Mock
    private SubscriberService subscriberService;

    @InjectMocks
    private SubscriberController subscriberController;

    private SubscriberRequest request;

    @BeforeEach
    void setUp() {
        request = SubscriberRequestFixture.기본_요청();
    }

    @Test
    void 구독_요청을_정상적으로_처리한다() {
        // given
        SubscriberResponse mockResponse = SubscriberResponseFixture.기본_요청();
        when(subscriberService.register(any())).thenReturn(mockResponse);

        // when
        ResponseEntity<SubscriberResponse> response = subscriberController.subscribe(request);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo("user@example.com");

        verify(subscriberService, times(1)).register(any());
    }
}