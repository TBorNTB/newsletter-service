package com.sejong.newsletterservice.core.csknowledge;

import com.sejong.newsletterservice.core.enums.MailCategoryName;
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
