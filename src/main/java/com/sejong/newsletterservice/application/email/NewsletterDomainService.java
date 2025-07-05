package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.core.csknowledge.CsKnowledge;
import com.sejong.newsletterservice.core.sentlog.SentLog;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.csknowledge.CsKnowledgeRepository;
import com.sejong.newsletterservice.core.common.RandomProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsletterDomainService {

    private final CsKnowledgeRepository csKnowledgeRepository;
    private final NewsletterEmailSender newsletterEmailSender;
    private final RandomProvider randomProvider;

    Optional<SentLog> sendNewsletterTo(Subscriber subscriber) {
        Optional<CsKnowledge> knowledge = subscriber.pickNextKnowledgeToSend(csKnowledgeRepository, randomProvider);
        if (knowledge.isPresent()) {
            return sendAndLog(subscriber, knowledge.get());
        }

        // 더이상 cs지식을 보내지 못할경우 아래 함수 선언
        return sendEmptyAndLog(subscriber);
    }

    private Optional<SentLog> sendEmptyAndLog(Subscriber subscriber) {
        newsletterEmailSender.send(subscriber.getEmail(), "<*>[뉴스레터] 지식이 없습니다 😢",null);
        return Optional.empty();
    }

    private Optional<SentLog> sendAndLog(Subscriber subscriber, CsKnowledge knowledge) {
        newsletterEmailSender.send(subscriber.getEmail(), knowledge.getTitle(), knowledge.getId());
        return Optional.of(SentLog.of(subscriber.getEmail(), knowledge.getId(), LocalDateTime.now()));
    }
}