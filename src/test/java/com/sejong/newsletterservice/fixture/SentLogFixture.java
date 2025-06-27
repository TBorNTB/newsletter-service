package com.sejong.newsletterservice.fixture;

import com.sejong.newsletterservice.core.sentlog.SentLog;
import com.sejong.newsletterservice.infrastructure.redis.dto.SentLogCacheDto;
import com.sejong.newsletterservice.infrastructure.redis.enums.SentLogStatus;

import java.time.LocalDateTime;

public class SentLogFixture {

    public static SentLog 로그_생성(Long id, String email, Long csKnowledgeId) {
        return SentLog.builder()
                .id(id)
                .email(email)
                .csKnowledgeId(csKnowledgeId)
                .sentAt(LocalDateTime.of(2024, 1, 1, 0, 0))
                .build();
    }

    public static SentLogCacheDto 캐시된_로그_생성(String email, Long csKnowledgeId) {
        return SentLogCacheDto.builder()
                .email(email)
                .csKnowledgeId(csKnowledgeId)
                .sentAt(LocalDateTime.of(2024, 1, 1, 0, 0))
                .status(SentLogStatus.PENDING)
                .build();
    }
}
