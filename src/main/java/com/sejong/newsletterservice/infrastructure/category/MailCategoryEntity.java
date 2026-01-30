package com.sejong.newsletterservice.infrastructure.category;

import com.sejong.newsletterservice.core.enums.TechCategory;
import com.sejong.newsletterservice.core.mailgategory.MailCategory;
import com.sejong.newsletterservice.infrastructure.subscriber.SubscriberEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "mailcategory")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id", nullable = false )
    private SubscriberEntity subscriber;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoryName",columnDefinition = "VARCHAR(50)", nullable = false)
    private TechCategory techCategory;

    public MailCategoryEntity(TechCategory techCategory) {
        this.techCategory = techCategory;
    }

    public static MailCategoryEntity from(MailCategory mailCategory, SubscriberEntity subscriber) {
        MailCategoryEntity mailCategoryEntity = new MailCategoryEntity(mailCategory.getTechCategory());

        mailCategoryEntity.assignSubscriber(subscriber);
        return mailCategoryEntity;
    }

    public MailCategory toDomain() {
        return MailCategory.builder()
                .id(id)
                .subscriberId(subscriber.getId())
                .techCategory(techCategory)
                .build();
    }

    private void assignSubscriber(SubscriberEntity subscriber) {
        this.subscriber = subscriber;
        subscriber.getMailCategories().add(this);
    }
}
