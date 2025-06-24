package com.sejong.newsletterservice.core.sentlog;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentLogRepository {
    boolean existsByEmailAndCsKnowledgeId(String email, Long csKnowledgeId);
    void save(SentLog sentLog);

    void saveAll(List<SentLog> sentLogs);
}
