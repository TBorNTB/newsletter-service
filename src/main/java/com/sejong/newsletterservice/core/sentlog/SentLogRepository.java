package com.sejong.newsletterservice.core.sentlog;

import java.util.List;

public interface SentLogRepository {
    boolean existsByEmailAndCsKnowledgeId(String email, Long csKnowledgeId);
    void save(SentLog sentLog);

    void saveAll(List<SentLog> sentLogs);
}
