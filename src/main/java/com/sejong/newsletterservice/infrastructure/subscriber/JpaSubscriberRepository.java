package com.sejong.newsletterservice.infrastructure.subscriber;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.subscriber.SubscriberRepository;
import java.util.Optional;
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

    @Override
    public Subscriber findOne(String email) {
        SubscriberEntity subscriberEntity = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 잘못된 요청"));
        return subscriberEntity.toDomain();
    }

    @Override
    public Optional<Subscriber> findOptional(String email) {
        return repository.findByEmail(email).map(SubscriberEntity::toDomain);
    }

    @Override
    public Subscriber save(Subscriber subscriber) {
        SubscriberEntity subscriberEntity = repository.findByEmail(subscriber.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 잘못된 요청"));

        if (Boolean.TRUE.equals(subscriber.getActive())) {
            subscriberEntity.registerActive();
        } else {
            subscriberEntity.cancelSubscribe();
        }
        if (subscriber.getEmailFrequency() != null) {
            subscriberEntity.updateEmailFrequency(subscriber.getEmailFrequency());
        }
        SubscriberEntity savedSubscriberEntity = repository.save(subscriberEntity);
        return savedSubscriberEntity.toDomain();
    }
}
