package com.sejong.newsletterservice.infrastructure.category;

import com.sejong.newsletterservice.core.mailgategory.MailCategory;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.mailgategory.MailCategoryRepository;
import com.sejong.newsletterservice.infrastructure.subscriber.SubscriberEntity;
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
