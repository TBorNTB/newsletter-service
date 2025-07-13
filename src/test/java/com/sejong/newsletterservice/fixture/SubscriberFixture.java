package com.sejong.newsletterservice.fixture;

import com.sejong.newsletterservice.core.mailgategory.MailCategory;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.enums.MailCategoryName;

import java.time.LocalDateTime;
import java.util.List;

public class SubscriberFixture {

    public static Subscriber 기본_구독자() {
        return Subscriber.of("user@example.com", EmailFrequency.DAILY, LocalDateTime.of(2024, 1, 1, 0, 0));
    }

    public static Subscriber 구독자_카테고리_포함() {
        return Subscriber.builder()
                .id(1L)
                .email("user@example.com")
                .emailFrequency(EmailFrequency.DAILY)
                .createdAt(LocalDateTime.of(2024, 1, 1, 0, 0))
                .mailCategories(List.of(
                        MailCategory.builder()
                                .id(1L)
                                .mailCategoryName(MailCategoryName.CRYPTOGRAPHY)
                                .subscriberId(1L)
                                .build(),
                        MailCategory.builder()
                                .id(2L)
                                .mailCategoryName(MailCategoryName.DIGITAL_FORENSICS)
                                .subscriberId(1L)
                                .build()
                ))
                .build();
    }

    public static Subscriber 구독자_카테고리_포함(Long subscriberId, String email) {
        return Subscriber.builder()
                .id(subscriberId)
                .email(email)
                .emailFrequency(EmailFrequency.DAILY)
                .createdAt(LocalDateTime.of(2024, 1, 1, 0, 0))
                .mailCategories(List.of(
                        MailCategory.builder()
                                .id(1L)
                                .mailCategoryName(MailCategoryName.CRYPTOGRAPHY)
                                .subscriberId(1L)
                                .build(),
                        MailCategory.builder()
                                .id(2L)
                                .mailCategoryName(MailCategoryName.DIGITAL_FORENSICS)
                                .subscriberId(1L)
                                .build()
                ))
                .build();
    }

    public static Subscriber 이메일_중복_구독자(String email) {
        return Subscriber.of(email, EmailFrequency.DAILY, LocalDateTime.now());
    }
}
