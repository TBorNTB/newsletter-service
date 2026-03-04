package com.sejong.newsletterservice.domains.subscribercategory.repository;

import com.sejong.newsletterservice.domains.subscriber.domain.Subscriber;
import com.sejong.newsletterservice.domains.subscribercategory.domain.SubscriberCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberCategoryRepository extends JpaRepository<SubscriberCategory, Long> {
    void deleteAllBySubscriber(Subscriber subscriber);
}
