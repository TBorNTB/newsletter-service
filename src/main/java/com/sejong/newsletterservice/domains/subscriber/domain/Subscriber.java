package com.sejong.newsletterservice.domains.subscriber.domain;

import com.sejong.newsletterservice.domains.subscribercategory.domain.SubscriberCategory;
import com.sejong.newsletterservice.support.common.EmailFrequency;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subscriber", indexes = {
        @Index(name = "idx_subscriber_email_frequency", columnList = "emailFrequency")
})
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private EmailFrequency emailFrequency;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Boolean chasingPopularity;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "subscriber", orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<SubscriberCategory> subscriberCategories = new ArrayList<>();

    public static Subscriber of(String email, EmailFrequency emailFrequency, Boolean chasingPopularity) {
        return Subscriber.builder()
                .email(email)
                .emailFrequency(emailFrequency)
                .chasingPopularity(chasingPopularity)
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public List<String> getCategoryNames() {
        return subscriberCategories.stream()
                .map(sc -> sc.getCategory().getName())
                .toList();
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

    public void updateChasingPopularity(Boolean chasingPopularity) {
        this.chasingPopularity = chasingPopularity;
    }
}
