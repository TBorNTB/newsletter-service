package com.sejong.newsletterservice.application.service;

import com.sejong.newsletterservice.application.email.EmailSender;
import com.sejong.newsletterservice.domain.model.CsKnowledge;
import com.sejong.newsletterservice.domain.model.MailCategory;
import com.sejong.newsletterservice.domain.model.SentLog;
import com.sejong.newsletterservice.domain.model.Subscriber;
import com.sejong.newsletterservice.domain.model.enums.EmailFrequency;
import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;
import com.sejong.newsletterservice.domain.repository.CsKnowledgeRepository;
import com.sejong.newsletterservice.domain.repository.SentLogRepository;
import com.sejong.newsletterservice.domain.repository.SubscriberRepository;
import com.sejong.newsletterservice.infrastructure.email.EmailNewsletterService;
import com.sejong.newsletterservice.util.RandomProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
