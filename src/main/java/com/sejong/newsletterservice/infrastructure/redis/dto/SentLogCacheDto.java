package com.sejong.newsletterservice.infrastructure.redis.dto;

import com.sejong.newsletterservice.infrastructure.redis.enums.SentLogStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentLogCacheDto implements Serializable {
    private String email;
    private Long csKnowledgeId;
    private LocalDateTime sentAt;
    private SentLogStatus status;

    public static SentLogCacheDto of(String email, Long csKnowledgeId, LocalDateTime sentAt, SentLogStatus status) {
        return new SentLogCacheDto(email, csKnowledgeId, sentAt, status);
    }
}
