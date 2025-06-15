package com.sejong.newsletterservice.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscriber {
    private Long id;
    private String email;
    private Long emailFrequency;
    private LocalDateTime createdAt;
    private List<MailCategory> mailCategorys;
}
