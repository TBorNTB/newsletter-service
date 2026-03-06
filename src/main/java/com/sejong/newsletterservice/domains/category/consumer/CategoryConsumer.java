package com.sejong.newsletterservice.domains.category.consumer;

import com.sejong.newsletterservice.domains.category.dto.event.CategoryEvent;
import com.sejong.newsletterservice.domains.category.dto.event.CategoryPayload;
import com.sejong.newsletterservice.domains.category.repository.CategoryRepository;
import com.sejong.newsletterservice.domains.subscribercategory.repository.SubscriberCategoryRepository;
import com.sejong.newsletterservice.support.common.GroupNames;
import com.sejong.newsletterservice.support.common.TopicNames;
import com.sejong.newsletterservice.support.common.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryConsumer {

    private final CategoryRepository categoryRepository;
    private final SubscriberCategoryRepository scRepository;

    @KafkaListener(
            topics = TopicNames.CATEGORY,
            groupId = GroupNames.CATEGORY
    )
    @Transactional
    public void consume(String message) {
        CategoryEvent event = CategoryEvent.fromJson(message);
        CategoryPayload payload = event.getCategoryPayload();

        if (event.getType() == Type.CREATED || event.getType() == Type.UPDATED) {
            categoryRepository.upsert(payload.getId(), payload.getName(), payload.getDescription(),
                    payload.getContent(), payload.getIconUrl());
        } else if (event.getType() == Type.DELETED) {
            scRepository.deleteByCategory_Id(event.getAggregateId()); // fk 제약 조건 위반 방지
            categoryRepository.deleteById(event.getAggregateId());
        }
    }
}
