package com.sejong.newsletterservice.fixture;

import com.sejong.newsletterservice.application.subscriber.dto.response.SubscriberResponse;

public class SubscriberResponseFixture {
    public static SubscriberResponse 기본_요청() {
        return new SubscriberResponse("user@example.com","구독이 정상적으로 완료되었습니다.");
    }
}
