package com.sejong.newsletterservice.domain.model;

import com.sejong.newsletterservice.domain.model.enums.EmailFrequency;
import com.sejong.newsletterservice.domain.model.vo.SubscriberRequestVO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscriber {
    private Long id;
    private String email;
    private EmailFrequency emailFrequency;
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
