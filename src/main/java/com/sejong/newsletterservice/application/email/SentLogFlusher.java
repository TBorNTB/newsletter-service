package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.core.sentlog.SentLog;
import com.sejong.newsletterservice.core.sentlog.SentLogRepository;
import com.sejong.newsletterservice.infrastructure.redis.SentLogCacheService;
import com.sejong.newsletterservice.infrastructure.redis.dto.SentLogCacheDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SentLogFlusher {

    private final SentLogRepository sentLogRepository;
    private final SentLogCacheService sentLogCacheService;

    @Transactional
    public void flushSuccessLogsToDatabase() {
        List<SentLogCacheDto> successLogs = sentLogCacheService.getAllSuccessLogsAndDelete();
        List<SentLog> sentLogs = successLogs.stream()
                .map(dto -> {
                    return SentLog.of(dto.getEmail(), dto.getCsKnowledgeId(), dto.getSentAt());
                }).toList();

        sentLogRepository.saveAll(sentLogs);
    }
}
