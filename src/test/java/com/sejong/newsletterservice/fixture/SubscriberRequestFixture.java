package com.sejong.newsletterservice.fixture;

import com.sejong.newsletterservice.application.subscriber.dto.request.SubscriberRequest;
import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.enums.MailCategoryName;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;

import java.util.List;


public class SubscriberRequestFixture {

    public static SubscriberRequest 기본_요청() {
        return new SubscriberRequest(
                "user@example.com",
                EmailFrequency.DAILY,
                List.of(MailCategoryName.CRYPTOGRAPHY, MailCategoryName.DIGITAL_FORENSICS)
        );
    }

    public static SubscriberRequestVO 기본_요청_vo() {
        return new SubscriberRequestVO(
                "user@example.com",
                EmailFrequency.DAILY,
                List.of(MailCategoryName.CRYPTOGRAPHY, MailCategoryName.DIGITAL_FORENSICS),
                "123456"
        );
    }

    public static SubscriberRequestVO 이메일_없음_vo() {
        return new SubscriberRequestVO(
                "", // 잘못된 이메일
                EmailFrequency.WEEKLY,
                List.of(MailCategoryName.CRYPTOGRAPHY),
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
}