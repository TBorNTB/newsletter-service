package com.sejong.newsletterservice.core.sentlog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SentLogTest {

    @DisplayName("SentLog의 정적 팩터리 메서드를 테스트 한다")
    @Test
    void SentLog의_정적_팩터리메서드는_정상_작동한다() {
        // given
        String email = "user@example.com";
        Long csKnowledgeId = 1L;
        LocalDateTime sentAt = LocalDateTime.of(2024,1,1,1,1,1);

        // when
        SentLog sentLog = SentLog.of(email, csKnowledgeId, sentAt);

        // then
        assertNotNull(sentLog);
        assertEquals(email, sentLog.getEmail());
        assertEquals(csKnowledgeId, sentLog.getCsKnowledgeId());
        assertEquals(sentAt, sentLog.getSentAt());
    }

}