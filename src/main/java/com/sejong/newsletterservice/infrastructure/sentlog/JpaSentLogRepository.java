package com.sejong.newsletterservice.infrastructure.sentlog;

import com.sejong.newsletterservice.core.sentlog.SentLog;
import com.sejong.newsletterservice.core.sentlog.SentLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaSentLogRepository implements SentLogRepository {

    private final SpringDataJpaSentLogRepository repository;

    @Override
    public boolean existsByEmailAndCsKnowledgeId(String email, Long csKnowledgeId) {
        return repository.existsByEmailAndCsKnowledgeId(email,csKnowledgeId);
    }

    @Override
    public void save(SentLog sentLog) {
        SentLogEntity sentLogEntity = SentLogEntity.from(sentLog);
        repository.save(sentLogEntity);
    }

    @Override
    public void saveAll(List<SentLog> sentLogs) {
        List<SentLogEntity> sentLogEntities = sentLogs.stream()
                .map(SentLogEntity::from)
                .toList();

        repository.saveAll(sentLogEntities);
    }
}
