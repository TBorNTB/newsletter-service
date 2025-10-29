package com.sejong.newsletterservice.infrastructure.subscriber;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.subscriber.SubscriberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaSubscriberRepository implements SubscriberRepository {

    private final SpringDataJpaSubscriberRepository repository;

    @Override
    public List<Subscriber> findByEmailFrequency(EmailFrequency frequency) {
        return repository.findByEmailFrequency(frequency).stream()
                .map(SubscriberEntity::toDomain)
                .toList();
    }

    @Override
    public List<Subscriber> findAll() {
        return repository.findAll().stream()
                .map(SubscriberEntity::toDomain)
                .toList();
    }
}
