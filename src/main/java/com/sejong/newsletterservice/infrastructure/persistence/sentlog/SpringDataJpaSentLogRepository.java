package com.sejong.newsletterservice.infrastructure.persistence.sentlog;

import com.sejong.newsletterservice.domain.model.CsKnowledge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaSentLogRepository  extends JpaRepository<SentLogEntity, Long> {
    boolean existsByEmailAndCsKnowledgeId(String email, Long csKnowledgeId);
}
