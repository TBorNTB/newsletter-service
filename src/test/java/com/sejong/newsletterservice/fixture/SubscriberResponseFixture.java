package com.sejong.newsletterservice.fixture;

import com.sejong.newsletterservice.api.controller.dto.request.SubscriberRequest;
import com.sejong.newsletterservice.api.controller.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.domain.model.enums.EmailFrequency;
import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;

import java.util.List;

public class SubscriberResponseFixture {
    public static SubscriberResponse 기본_요청() {
        return new SubscriberResponse("user@example.com","구독이 정상적으로 완료되었습니다.");
    }
}
