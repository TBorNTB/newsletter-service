package com.sejong.newsletterservice.infrastructure.persistence.category;

import com.sejong.newsletterservice.domain.model.MailCategory;
import com.sejong.newsletterservice.domain.model.Subscriber;
import com.sejong.newsletterservice.domain.repository.MailCategoryRepository;
import com.sejong.newsletterservice.infrastructure.persistence.subscriber.SubscriberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaMailCategoryRepository implements MailCategoryRepository {
    private final SpringDataJpaMailCategoryRepository mailCategoryRepository;

    @Override
    public Subscriber saveCategoriesTo(Subscriber subscriber, List<MailCategory> mailCategories) {
        SubscriberEntity subscriberEntity = SubscriberEntity.from(subscriber);

        List<MailCategoryEntity> categoryEntities = mailCategories.stream()
                .map(category -> MailCategoryEntity.from(category, subscriberEntity))
                .toList();

        mailCategoryRepository.saveAll(categoryEntities);
        return subscriberEntity.toDomain();
    }
}
