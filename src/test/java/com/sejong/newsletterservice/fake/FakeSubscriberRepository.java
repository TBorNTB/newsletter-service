package com.sejong.newsletterservice.fake;

import com.sejong.newsletterservice.domain.model.Subscriber;
import com.sejong.newsletterservice.domain.repository.SubscriberRepository;

import java.util.*;

public class FakeSubscriberRepository implements SubscriberRepository {

    private final Map<Long, Subscriber> store = new HashMap<>();
    private long sequence = 1L;

    public Subscriber save(Subscriber subscriber) {
        Long id = subscriber.getId() != null ? subscriber.getId() : sequence++;

        Subscriber saved = Subscriber.builder()
                .id(id)
                .email(subscriber.getEmail())
                .emailFrequency(subscriber.getEmailFrequency())
                .createdAt(subscriber.getCreatedAt())
                .mailCategories(subscriber.getMailCategories())
                .build();

        store.put(id, saved);
        return saved;
    }


    public Optional<Subscriber> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }


    public List<Subscriber> findAll() {
        return new ArrayList<>(store.values());
    }


    public void deleteById(Long id) {
        store.remove(id);
    }

    public void clear() {
        store.clear();
        sequence = 1L;
    }
}
