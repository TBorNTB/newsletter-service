package com.sejong.newsletterservice.infrastructure.persistence.subscriber;

import com.sejong.newsletterservice.domain.model.MailCategory;
import com.sejong.newsletterservice.domain.model.Subscriber;
import com.sejong.newsletterservice.domain.model.enums.EmailFrequency;
import com.sejong.newsletterservice.infrastructure.persistence.category.MailCategoryEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subscriber")
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
    @Column(columnDefinition = "varchar(50)",nullable = false)
    private EmailFrequency emailFrequency;

    @DateTimeFormat
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "subscriber",orphanRemoval = true , fetch = FetchType.LAZY)
    private List<MailCategoryEntity> mailCategories = new ArrayList<>();

    public static SubscriberEntity from(Subscriber subscriber) {
        return SubscriberEntity.builder()
                .email(subscriber.getEmail())
                .emailFrequency(subscriber.getEmailFrequency())
                .createdAt(subscriber.getCreatedAt())
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
                .build();
    }
}
