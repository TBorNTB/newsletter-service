package com.sejong.newsletterservice.infrastructure.category;

import com.sejong.newsletterservice.core.mailgategory.MailCategory;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.mailgategory.MailCategoryRepository;
import com.sejong.newsletterservice.infrastructure.subscriber.SpringDataJpaSubscriberRepository;
import com.sejong.newsletterservice.infrastructure.subscriber.SubscriberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaMailCategoryRepository implements MailCategoryRepository {
    private final SpringDataJpaMailCategoryRepository mailCategoryRepository;
    private final SpringDataJpaSubscriberRepository subscriberRepository;

    @Override
    public Subscriber saveCategoriesTo(Subscriber subscriber, List<MailCategory> mailCategories) {
        Optional<SubscriberEntity> subscriberEntityOptional = subscriberRepository.findByEmail(subscriber.getEmail());

        if(subscriberEntityOptional.isPresent()){
            SubscriberEntity subscriberEntity = subscriberEntityOptional.get();
            subscriberEntity.registerActive();
            subscriberEntity.updateEmailFrequency(subscriber.getEmailFrequency());

            subscriberEntity.getMailCategories().clear();
            List<MailCategoryEntity> categoryEntities = mailCategories.stream()
                    .map(category -> MailCategoryEntity.from(category, subscriberEntity))
                    .toList();

            mailCategoryRepository.saveAll(categoryEntities);
            SubscriberEntity savedSubscriberEntity = subscriberRepository.save(subscriberEntity);
            return savedSubscriberEntity.toDomain();
        }

        SubscriberEntity subscriberEntity = subscriberRepository.save(SubscriberEntity.from(subscriber));

        List<MailCategoryEntity> categoryEntities = mailCategories.stream()
                .map(category -> MailCategoryEntity.from(category, subscriberEntity))
                .toList();

        mailCategoryRepository.saveAll(categoryEntities);
        SubscriberEntity savedSubscriberEntity = subscriberRepository.save(subscriberEntity);
        return savedSubscriberEntity.toDomain();
    }

}
