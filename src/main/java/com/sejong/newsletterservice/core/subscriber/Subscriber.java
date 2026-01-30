package com.sejong.newsletterservice.core.subscriber;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.enums.TechCategory;
import com.sejong.newsletterservice.core.mailgategory.MailCategory;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscriber {
    private Long id;
    private String email;
    private EmailFrequency emailFrequency;
    private Boolean chasingPopularity;
    private LocalDateTime createdAt;
    private Boolean active;
    @Builder.Default
    private List<MailCategory> mailCategories = new ArrayList<>();

    public static Subscriber of(String email, EmailFrequency emailFrequency, LocalDateTime createdAt) {
        return Subscriber.builder().
                email(email)
                .emailFrequency(emailFrequency)
                .createdAt(createdAt)
                .active(true)
                .build();
    }

    public static Subscriber from(SubscriberRequestVO requestV0, LocalDateTime createdAt) {
        return Subscriber.builder()
                .email(requestV0.email())
                // 왜 category가 없지? 흠..
                .emailFrequency(requestV0.emailFrequency())
                .chasingPopularity(requestV0.chasingPopularity())
                .createdAt(createdAt)
                .active(true)
                .build();
    }

    public static Subscriber updatePreferencesFrom(Subscriber existing, EmailFrequency emailFrequency) {
        return Subscriber.builder()
                .id(existing.getId())
                .email(existing.getEmail())
                .emailFrequency(emailFrequency)
                .chasingPopularity(existing.getChasingPopularity())
                .createdAt(existing.getCreatedAt())
                .active(existing.getActive())
                .mailCategories(existing.getMailCategories())
                .build();
    }

    public List<TechCategory> getSubscribedTechCategories() {
        return mailCategories.stream().map(MailCategory::getTechCategory).toList();
    }

    public void cancelSubscribe() {
        this.active=false;

    }
}
