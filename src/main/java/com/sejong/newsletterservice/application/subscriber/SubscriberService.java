package com.sejong.newsletterservice.application.subscriber;

import com.sejong.newsletterservice.application.subscriber.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.application.subscriber.dto.response.SubscriberStatusResponse;
import com.sejong.newsletterservice.core.error.code.ErrorCode;
import com.sejong.newsletterservice.core.error.exception.ApiException;
import com.sejong.newsletterservice.core.mailgategory.MailCategory;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.subscriber.SubscriberRepository;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.core.mailgategory.MailCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriberService {
    private final MailCategoryRepository mailCategoryRepository;
    private final SubscriberRepository subscriberRepository;

    @Transactional
    public SubscriberResponse register(SubscriberRequestVO subscriberRequestVO) {

        Subscriber subscriber = Subscriber.from(subscriberRequestVO, LocalDateTime.now());
        List<MailCategory> mailCategories = subscriberRequestVO.selectedCategories().stream()
                .map(MailCategory::of)
                .toList();

        Subscriber resultSubscriber = mailCategoryRepository.saveCategoriesTo(subscriber, mailCategories);
        return SubscriberResponse.from(resultSubscriber);
    }

    @Transactional
    public SubscriberResponse deleteSubscriber(String email) {
        Subscriber subscriber = subscriberRepository.findOne(email);
        subscriber.cancelSubscribe();

        Subscriber savedSubscriber = subscriberRepository.save(subscriber);
        return SubscriberResponse.deletedFrom(savedSubscriber);
    }

    @Transactional
    public SubscriberResponse updatePreferences(SubscriberRequestVO verifiedVO) {
        Subscriber subscriber = subscriberRepository.findOne(verifiedVO.email());
        if (!Boolean.TRUE.equals(subscriber.getActive())) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "구독 해제된 사용자입니다. 재구독은 인증 후 진행해주세요.");
        }

        Subscriber updatedSubscriber = Subscriber.updatePreferencesFrom(subscriber, verifiedVO.emailFrequency());
        List<MailCategory> mailCategories = verifiedVO.selectedCategories().stream()
                .map(MailCategory::of)
                .toList();

        Subscriber saved = mailCategoryRepository.saveCategoriesTo(updatedSubscriber, mailCategories);
        return SubscriberResponse.updatedFrom(saved);
    }

    @Transactional(readOnly = true)
    public SubscriberStatusResponse getStatus(String email) {
        return subscriberRepository.findOptional(email)
                .map(SubscriberStatusResponse::from)
                .orElseGet(() -> SubscriberStatusResponse.notRegistered(email));
    }
}
