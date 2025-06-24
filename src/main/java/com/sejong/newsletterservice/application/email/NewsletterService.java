package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.core.sentlog.SentLog;
import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.sentlog.SentLogRepository;
import com.sejong.newsletterservice.core.subscriber.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsletterService {

    private final SubscriberRepository subscriberRepository;
    private final NewsletterDomainService newsletterDomainService;
    private final SentLogRepository sentLogRepository;

    @Transactional
    public void sendNewsletters(EmailFrequency frequency) {
        List<SentLog> sentLogs = subscriberRepository.findByEmailFrequency(frequency).stream()
                .map(newsletterDomainService::sendNewsletterTo)
                .flatMap(Optional::stream)
                .toList();

        sentLogRepository.saveAll(sentLogs);
    }
}
