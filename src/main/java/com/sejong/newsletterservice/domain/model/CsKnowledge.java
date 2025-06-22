package com.sejong.newsletterservice.domain.model;

import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CsKnowledge {

    private Long id;
    private String title;
    private String content;
    private MailCategoryName category;
    private LocalDateTime createdAt;
}
