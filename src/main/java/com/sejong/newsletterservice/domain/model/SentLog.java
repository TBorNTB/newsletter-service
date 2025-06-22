package com.sejong.newsletterservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SentLog {

    private Long id;
    private String email;
    private Long csKnowledgeId;
    private LocalDateTime sentAt;

    public static SentLog of(String email, Long csKnowledgeId, LocalDateTime sentAt) {
        return SentLog.builder()
                .email(email)
                .csKnowledgeId(csKnowledgeId)
                .sentAt(sentAt)
                .build();
    }
}
