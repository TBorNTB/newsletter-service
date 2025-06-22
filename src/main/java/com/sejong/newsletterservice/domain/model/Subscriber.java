package com.sejong.newsletterservice.domain.model;

import com.sejong.newsletterservice.domain.model.enums.EmailFrequency;
import com.sejong.newsletterservice.domain.model.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.domain.repository.CsKnowledgeRepository;
import com.sejong.newsletterservice.util.RandomProvider;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public Optional<CsKnowledge> pickNextKnowledgeToSend(CsKnowledgeRepository repo, RandomProvider rand) {
        List<MailCategory> shuffled = new ArrayList<>(mailCategories);
        Collections.shuffle(shuffled, rand.getRandom());

        for (MailCategory cat : shuffled) {
            Optional<CsKnowledge> knowledge = repo.findUnsentKnowledge(cat.getMailCategoryName(), email);
            if (knowledge.isPresent()) return knowledge;
        }
        return Optional.empty();
    }
}
