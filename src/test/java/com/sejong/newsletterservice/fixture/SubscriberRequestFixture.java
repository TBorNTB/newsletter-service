package com.sejong.newsletterservice.fixture;

import com.sejong.newsletterservice.application.subscriber.dto.request.SubscriptionRequest;
import com.sejong.newsletterservice.application.subscriber.dto.request.VerifyRequest;
import com.sejong.newsletterservice.application.subscriber.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.enums.TechCategory;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import java.util.List;


public class SubscriberRequestFixture {

    public static SubscriptionRequest 기본_요청() {
        return new SubscriptionRequest(
                "user@example.com",
                EmailFrequency.DAILY,
                List.of(TechCategory.CRYPTOGRAPHY, TechCategory.DIGITAL_FORENSICS)
        );
    }

    public static SubscriberRequestVO 기본_요청_vo() {
        return new SubscriberRequestVO(
                "user@example.com",
                EmailFrequency.DAILY,
                List.of(TechCategory.CRYPTOGRAPHY, TechCategory.DIGITAL_FORENSICS),
                "123456"
        );
    }

    public static SubscriberRequestVO 이메일_없음_vo() {
        return new SubscriberRequestVO(
                "", // 잘못된 이메일
                EmailFrequency.WEEKLY,
                List.of(TechCategory.CRYPTOGRAPHY),
                "123456"
        );
    }

    public static SubscriberRequestVO 카테고리_없음_vo() {
        return new SubscriberRequestVO(
                "user@example.com",
                EmailFrequency.WEEKLY,
                List.of(), // 빈 카테고리
                "123456"
        );
    }

    public static VerifyRequest verifyRequest() {
        return new VerifyRequest(
                "user@example.com",
                "123456"
        );
    }

    public static SubscriberResponse subscriberResponse() {
        return SubscriberResponse.builder()
                .email("user@example.com")
                .message("구독이 정상적으로 완료되었습니다.")
                .build();
    }
}