package com.sejong.newsletterservice.infrastructure.persistence.subscriber;

import com.sejong.newsletterservice.domain.model.Subscriber;
import com.sejong.newsletterservice.domain.model.enums.EmailFrequency;
import com.sejong.newsletterservice.domain.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaSubscriberRepository implements SubscriberRepository {

    private final SpringDataJpaSubscriberRepository repository;

    @Override
    public List<Subscriber> findByEmailFrequency(EmailFrequency frequency) {
        return repository.findByEmailFrequencyWithCategories(frequency).stream()
                .map(SubscriberEntity::toDomain)
                .toList();
    }
}
