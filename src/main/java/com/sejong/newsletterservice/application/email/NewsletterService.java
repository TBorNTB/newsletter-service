package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.enums.TechCategory;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.subscriber.SubscriberRepository;
import com.sejong.newsletterservice.infrastructure.feign.ElasticServiceClient;
import com.sejong.newsletterservice.infrastructure.feign.response.ContentResponse;
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
        List<Subscriber> subscribers = subscriberRepository.findByEmailFrequency(frequency);
        subscribers.forEach(s -> send(s, content));
        return (long)subscribers.size();
    }

    @Transactional(readOnly = true)
    public Long sendInterestingContents(EmailFrequency frequency) {
        List<Subscriber> subscribers = subscriberRepository.findByEmailFrequency(frequency);
        subscribers.forEach(s -> {
            List<TechCategory> techCategories = s.getSubscribedTechCategories();
            List<ContentResponse> contents = elasticServiceClient.getInterestingContent(techCategories);

        });
        return (long)subscribers.size();
    }


    private void send(Subscriber subscriber, ContentResponse content) {
        newsletterEmailSender.send(subscriber.getEmail(), content.getTitle(), content.getId());
    }
}
