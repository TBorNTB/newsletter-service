package com.sejong.newsletterservice.fake;

import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.subscriber.SubscriberRepository;

import java.util.*;

public class FakeSubscriberRepository implements SubscriberRepository {

    private final Map<Long, Subscriber> store = new HashMap<>();
    private long sequence = 1L;

    @Override
    public Subscriber save(Subscriber subscriber) {
        Long id = subscriber.getId() != null ? subscriber.getId() : sequence++;

        Subscriber saved = Subscriber.builder()
                .id(id)
                .email(subscriber.getEmail())
                .emailFrequency(subscriber.getEmailFrequency())
                .createdAt(subscriber.getCreatedAt())
                .active(subscriber.getActive())
                .mailCategories(subscriber.getMailCategories())
                .build();

        store.put(id, saved);
        return saved;
    }


    public Optional<Subscriber> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }


    @Override
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

    @Override
    public List<Subscriber> findByEmailFrequency(EmailFrequency frequency) {
        return store.values().stream()
                .filter(s -> Objects.equals(s.getEmailFrequency(), frequency))
                .toList();
    }

    @Override
    public Subscriber findOne(String email) {
        return findOptional(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 잘못된 요청"));
    }

    @Override
    public Optional<Subscriber> findOptional(String email) {
        return store.values().stream()
                .filter(s -> Objects.equals(s.getEmail(), email))
                .findFirst();
    }
}
