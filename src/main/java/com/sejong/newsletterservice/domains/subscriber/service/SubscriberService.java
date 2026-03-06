package com.sejong.newsletterservice.domains.subscriber.service;

import com.sejong.newsletterservice.domains.category.domain.Category;
import com.sejong.newsletterservice.domains.category.repository.CategoryRepository;
import com.sejong.newsletterservice.domains.subscriber.domain.Subscriber;
import com.sejong.newsletterservice.domains.subscriber.dto.request.SubscriberRequestVO;
import com.sejong.newsletterservice.domains.subscriber.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.domains.subscriber.dto.response.SubscriberStatusResponse;
import com.sejong.newsletterservice.domains.subscriber.repository.SubscriberRepository;
import com.sejong.newsletterservice.domains.subscribercategory.domain.SubscriberCategory;
import com.sejong.newsletterservice.domains.subscribercategory.repository.SubscriberCategoryRepository;
import com.sejong.newsletterservice.support.exception.ApiException;
import com.sejong.newsletterservice.support.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final CategoryRepository categoryRepository;
    private final SubscriberCategoryRepository subscriberCategoryRepository;

    @Transactional
    public SubscriberResponse register(SubscriberRequestVO vo) {
        List<Category> categories = categoryRepository.findAllByNameIn(vo.selectedCategories());

        Subscriber subscriber = subscriberRepository.findByEmail(vo.email())
                .orElseGet(() -> subscriberRepository.save(
                        Subscriber.of(vo.email(), vo.emailFrequency(), vo.chasingPopularity())
                ));

        subscriber.registerActive();
        subscriber.updateEmailFrequency(vo.emailFrequency());
        subscriber.updateChasingPopularity(vo.chasingPopularity());

        subscriberCategoryRepository.deleteAllBySubscriber(subscriber);

        List<SubscriberCategory> subscriberCategories = categories.stream()
                .map(c -> SubscriberCategory.of(subscriber, c))
                .toList();
        subscriberCategoryRepository.saveAll(subscriberCategories);

        return SubscriberResponse.registered(subscriber.getEmail());
    }

    @Transactional
    public SubscriberResponse deleteSubscriber(String email) {
        Subscriber subscriber = findByEmailOrThrow(email);
        subscriber.cancelSubscribe();
        return SubscriberResponse.cancelled(subscriber.getEmail());
    }

    @Transactional
    public SubscriberResponse updatePreferences(SubscriberRequestVO vo) {
        Subscriber subscriber = findByEmailOrThrow(vo.email());

        if (!Boolean.TRUE.equals(subscriber.getActive())) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "구독 해제된 사용자입니다. 재구독은 인증 후 진행해주세요.");
        }

        List<Category> categories = categoryRepository.findAllByNameIn(vo.selectedCategories());

        subscriber.updateEmailFrequency(vo.emailFrequency());
        subscriber.updateChasingPopularity(vo.chasingPopularity());

        subscriberCategoryRepository.deleteAllBySubscriber(subscriber);

        List<SubscriberCategory> subscriberCategories = categories.stream()
                .map(c -> SubscriberCategory.of(subscriber, c))
                .toList();
        subscriberCategoryRepository.saveAll(subscriberCategories);

        return SubscriberResponse.updated(subscriber.getEmail());
    }

    @Transactional(readOnly = true)
    public SubscriberStatusResponse getStatus(String email) {
        return subscriberRepository.findByEmail(email)
                .map(SubscriberStatusResponse::from)
                .orElseGet(() -> SubscriberStatusResponse.notRegistered(email));
    }

    private Subscriber findByEmailOrThrow(String email) {
        return subscriberRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "등록된 이메일이 없습니다: " + email));
    }
}
