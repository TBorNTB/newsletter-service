package com.sejong.newsletterservice.application.subscriber;

import com.sejong.newsletterservice.application.subscriber.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.core.mailgategory.MailCategory;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.core.mailgategory.MailCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriberService {
    private final MailCategoryRepository mailCategoryRepository;

    public SubscriberResponse register(SubscriberRequestVO subscriberRequestVO) {
        Subscriber subscriber = Subscriber.from(subscriberRequestVO, LocalDateTime.now());
        List<MailCategory> mailCategories = subscriberRequestVO.selectedCategories().stream()
                .map(MailCategory::of)
                .toList();

        Subscriber resultSubscriber = mailCategoryRepository.saveCategoriesTo(subscriber, mailCategories);
        return SubscriberResponse.from(resultSubscriber);
    }
}
