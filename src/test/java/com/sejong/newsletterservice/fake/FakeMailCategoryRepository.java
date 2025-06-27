package com.sejong.newsletterservice.fake;

import com.sejong.newsletterservice.core.mailgategory.MailCategory;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.mailgategory.MailCategoryRepository;

import java.util.*;

public class FakeMailCategoryRepository implements MailCategoryRepository {

    private final Map<Long, MailCategory> store = new HashMap<>();
    private long sequence = 1L;

    @Override
    public Subscriber saveCategoriesTo(Subscriber subscriber, List<MailCategory> mailCategories) {
        List<MailCategory> savedCategories = new ArrayList<>();

        for (MailCategory category : mailCategories) {
            Long id = category.getId() != null ? category.getId() : sequence++;
            MailCategory saved = MailCategory.builder()
                    .id(id)
                    .mailCategoryName(category.getMailCategoryName())
                    .subscriberId(subscriber.getId())
                    .build();

            store.put(id, saved);
            savedCategories.add(saved);
        }

        return Subscriber.builder()
                .id(subscriber.getId())
                .email(subscriber.getEmail())
                .emailFrequency(subscriber.getEmailFrequency())
                .createdAt(subscriber.getCreatedAt())
                .mailCategories(savedCategories)
                .build();
    }

    public Optional<MailCategory> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<MailCategory> findAll() {
        return new ArrayList<>(store.values());
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public void deleteBySubscriberId(Long subscriberId) {
        List<Long> toDelete = store.values().stream()
                .filter(mc -> subscriberId.equals(mc.getSubscriberId()))
                .map(MailCategory::getId)
                .toList();

        toDelete.forEach(store::remove);
    }

    // 전체 삭제
    public void clear() {
        store.clear();
        sequence = 1L;
    }
}

