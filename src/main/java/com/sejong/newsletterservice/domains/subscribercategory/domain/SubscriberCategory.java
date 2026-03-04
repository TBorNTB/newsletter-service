package com.sejong.newsletterservice.domains.subscribercategory.domain;

import com.sejong.newsletterservice.domains.category.domain.Category;
import com.sejong.newsletterservice.domains.subscriber.domain.Subscriber;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscriber_category")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriberCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id", nullable = false)
    private Subscriber subscriber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public static SubscriberCategory of(Subscriber subscriber, Category category) {
        return SubscriberCategory.builder()
                .subscriber(subscriber)
                .category(category)
                .build();
    }
}
