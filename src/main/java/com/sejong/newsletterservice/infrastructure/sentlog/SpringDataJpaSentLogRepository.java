package com.sejong.newsletterservice.infrastructure.sentlog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaSentLogRepository  extends JpaRepository<SentLogEntity, Long> {
    boolean existsByEmailAndCsKnowledgeId(String email, Long csKnowledgeId);
}
