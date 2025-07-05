package com.sejong.newsletterservice.infrastructure.sentlog;

import com.sejong.newsletterservice.core.sentlog.SentLog;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "sent_log",
        indexes = {
                @Index(name = "idx_sent_log_email_knowledge", columnList = "email, csKnowledgeId")
        }
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SentLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private Long csKnowledgeId;

    private LocalDateTime sentAt;

    public static SentLogEntity from(SentLog sentLog) {
        return SentLogEntity.builder()
                .email(sentLog.getEmail())
                .csKnowledgeId(sentLog.getCsKnowledgeId())
                .sentAt(sentLog.getSentAt())
                .build();
    }
}
