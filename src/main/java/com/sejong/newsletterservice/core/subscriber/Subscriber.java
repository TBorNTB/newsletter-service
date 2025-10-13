package com.sejong.newsletterservice.core.subscriber;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
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
    private List<MailCategory> mailCategories = new ArrayList<>();

    public static Subscriber of(String email, EmailFrequency emailFrequency, LocalDateTime createdAt) {
        return Subscriber.builder().
                email(email)
                .emailFrequency(emailFrequency)
                .createdAt(createdAt)
                .build();
    }

    public static Subscriber from(SubscriberRequestVO requestV0, LocalDateTime createdAt) {
        return Subscriber.builder()
                .email(requestV0.email())
                .emailFrequency(requestV0.emailFrequency())
                .createdAt(createdAt)
                .build();
    }
}
