package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.application.internal.MetaExternalService;
import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.subscriber.SubscriberRepository;
import com.sejong.newsletterservice.infrastructure.feign.response.MetaVisitersAllResponse;
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
    private final NewsletterEmailSender newsletterEmailSender;
    private final MetaExternalService metaExternalService;

    @Transactional
    public Long sendNewsletters(EmailFrequency frequency) {
        return subscriberRepository.findByEmailFrequency(frequency).stream()
                .map(newsletterDomainService::sendNewsletterTo)
                .filter(Optional::isPresent)
                .count();

    }

    public void sendMostViewPost() {

        MetaVisitersAllResponse response = metaExternalService.getMostVisiters();

        String title = "🔥이번 주 인기글!";

        List<Subscriber> subscribers = subscriberRepository.findAll();
        for (Subscriber subscriber : subscribers) {
            newsletterEmailSender.sendMostVisiters(
                    subscriber.getEmail(),
                    title,
                    response
            );

            // 로그 저장 필요 시 여기에 SentLog 저장 로직 추가
        }
    }
}
