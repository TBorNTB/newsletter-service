package com.sejong.newsletterservice.fixture;

import com.sejong.newsletterservice.api.controller.dto.request.SubscriberRequest;
import com.sejong.newsletterservice.domain.model.enums.EmailFrequency;
import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;
import com.sejong.newsletterservice.domain.model.vo.SubscriberRequestVO;

import java.time.LocalDateTime;
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