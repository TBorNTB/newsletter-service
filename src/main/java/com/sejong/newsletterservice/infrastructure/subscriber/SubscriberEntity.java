package com.sejong.newsletterservice.infrastructure.subscriber;

import com.sejong.newsletterservice.core.mailgategory.MailCategory;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.infrastructure.category.MailCategoryEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subscriber", indexes = {
        @Index(name = "idx_subscriber_email_frequency", columnList = "emailFrequency")
})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private EmailFrequency emailFrequency;

    @DateTimeFormat
    private LocalDateTime createdAt;

    @Column(nullable= false)
    private Boolean active;

    @OneToMany(mappedBy = "subscriber", orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<MailCategoryEntity> mailCategories = new ArrayList<>();

    public static SubscriberEntity from(Subscriber subscriber) {
        return SubscriberEntity.builder()
                .email(subscriber.getEmail())
                .emailFrequency(subscriber.getEmailFrequency())
                .createdAt(subscriber.getCreatedAt())
                .active(subscriber.getActive())
                .mailCategories(new ArrayList<>())
                .build();
    }

    public Subscriber toDomain() {
        List<MailCategory> domainCategories = mailCategories.stream()
                .map(MailCategoryEntity::toDomain)
                .toList();

        return Subscriber.builder()
                .id(id)
                .email(email)
                .emailFrequency(emailFrequency)
                .createdAt(createdAt)
                .mailCategories(domainCategories)
                .active(active)
                .build();
    }

    public void cancelSubscribe() {
        this.active = false;
    }

    public void registerActive() {
        this.active = true;
    }

    public void updateEmailFrequency(EmailFrequency emailFrequency) {
        this.emailFrequency = emailFrequency;
    }
}
