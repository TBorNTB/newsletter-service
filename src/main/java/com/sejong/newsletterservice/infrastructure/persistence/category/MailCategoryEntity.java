package com.sejong.newsletterservice.infrastructure.persistence.category;

import com.sejong.newsletterservice.domain.model.MailCategory;
import com.sejong.newsletterservice.domain.model.Subscriber;
import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;
import com.sejong.newsletterservice.infrastructure.persistence.subscriber.SubscriberEntity;
import jakarta.persistence.*;
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

    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id", nullable = false )
    private SubscriberEntity subscriber;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private MailCategoryName mailCategoryName;

    public MailCategoryEntity(MailCategoryName mailCategoryName) {
        this.mailCategoryName = mailCategoryName;
    }

    public static MailCategoryEntity from(MailCategory mailCategory, SubscriberEntity subscriber) {
        MailCategoryEntity mailCategoryEntity = new MailCategoryEntity(mailCategory.getMailCategoryName());

        mailCategoryEntity.assignSubscriber(subscriber);
        return mailCategoryEntity;
    }

    public MailCategory toDomain() {
        return MailCategory.builder()
                .id(id)
                .subscriberId(subscriber.getId())
                .mailCategoryName(mailCategoryName)
                .build();
    }

    private void assignSubscriber(SubscriberEntity subscriber) {
        this.subscriber = subscriber;
        subscriber.getMailCategories().add(this);
    }
}
