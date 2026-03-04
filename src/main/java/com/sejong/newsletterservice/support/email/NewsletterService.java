package com.sejong.newsletterservice.support.email;

import com.sejong.newsletterservice.domains.subscriber.domain.Subscriber;
import com.sejong.newsletterservice.domains.subscriber.repository.SubscriberRepository;
import com.sejong.newsletterservice.support.common.EmailFrequency;
import com.sejong.newsletterservice.support.feign.ElasticServiceClient;
import com.sejong.newsletterservice.support.feign.response.ContentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsletterService {

    private final SubscriberRepository subscriberRepository;
    private final NewsletterEmailSender newsletterEmailSender;
    private final ElasticServiceClient elasticServiceClient;

    @Transactional(readOnly = true)
    public Long sendPopularContents(EmailFrequency frequency) {
        ContentResponse content = elasticServiceClient.getWeeklyPopularContent();
        List<Subscriber> subscribers = subscriberRepository.findActiveByEmailFrequency(frequency);
        subscribers.forEach(s -> newsletterEmailSender.sendPopularContent(s.getEmail(), "주간 인기글", content));
        return (long) subscribers.size();
    }

    @Transactional(readOnly = true)
    public Long sendInterestingContents(EmailFrequency frequency) {
        List<Subscriber> subscribers = subscriberRepository.findActiveByEmailFrequency(frequency);
        subscribers.forEach(s -> {
            List<ContentResponse> contents = elasticServiceClient.getInterestingContent(s.getCategoryNames());
            newsletterEmailSender.sendInterestingCategoryContents(s.getEmail(), "구독한 카테고리 글", contents);
        });
        return (long) subscribers.size();
    }
}
