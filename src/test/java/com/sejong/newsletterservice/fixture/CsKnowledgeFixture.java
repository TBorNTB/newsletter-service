package com.sejong.newsletterservice.fixture;

import com.sejong.newsletterservice.core.csknowledge.CsKnowledge;
import com.sejong.newsletterservice.core.enums.MailCategoryName;

import java.time.LocalDateTime;

public class CsKnowledgeFixture {

    public static CsKnowledge 기본_지식() {
        return CsKnowledge.builder()
                .id(1L)
                .title("테스트제목")
                .content("테스트내용")
                .category(MailCategoryName.CRYPTOGRAPHY)
                .createdAt(LocalDateTime.of(2024, 1, 1, 0, 0))
                .build();
    }
}
