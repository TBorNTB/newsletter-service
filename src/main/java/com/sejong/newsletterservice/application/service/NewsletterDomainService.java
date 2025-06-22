package com.sejong.newsletterservice.application.service;

import com.sejong.newsletterservice.application.email.EmailSender;
import com.sejong.newsletterservice.domain.model.CsKnowledge;
import com.sejong.newsletterservice.domain.model.MailCategory;
import com.sejong.newsletterservice.domain.model.SentLog;
import com.sejong.newsletterservice.domain.model.Subscriber;
import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;
import com.sejong.newsletterservice.domain.repository.CsKnowledgeRepository;
import com.sejong.newsletterservice.util.RandomProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NewsletterDomainService {

    private final CsKnowledgeRepository csKnowledgeRepository;
    private final EmailSender emailSender;
    private final RandomProvider randomProvider;

    public NewsletterDomainService(
            CsKnowledgeRepository csKnowledgeRepository,
            @Qualifier("newsletterSender") EmailSender emailSender,
            RandomProvider randomProvider
    ) {
        this.csKnowledgeRepository = csKnowledgeRepository;
        this.emailSender = emailSender;
        this.randomProvider = randomProvider;
    }

    Optional<SentLog> sendNewsletterTo(Subscriber subscriber) {
        Optional<CsKnowledge> knowledge = subscriber.pickNextKnowledgeToSend(csKnowledgeRepository, randomProvider);
        if (knowledge.isPresent()) {
            return sendAndLog(subscriber, knowledge.get());
        }

        // 더이상 cs지식을 보내지 못할경우 아래 함수 선언
        return sendEmptyAndLog(subscriber);
    }

    private Optional<SentLog> sendEmptyAndLog(Subscriber subscriber) {
        emailSender.send(subscriber.getEmail(), "<*>[뉴스레터] 지식이 없습니다 😢");
        return Optional.empty();
    }

    private Optional<SentLog> sendAndLog(Subscriber subscriber, CsKnowledge knowledge) {
        emailSender.send(subscriber.getEmail(), knowledge.getTitle());
        return Optional.of(SentLog.of(subscriber.getEmail(), knowledge.getId(), LocalDateTime.now()));
    }
}