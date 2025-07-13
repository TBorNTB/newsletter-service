package com.sejong.newsletterservice.core.mailgategory;

import com.sejong.newsletterservice.core.enums.MailCategoryName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailCategory {
    private Long id;
    private Long subscriberId;
    private MailCategoryName mailCategoryName;

    public static MailCategory of(MailCategoryName mailCategoryName) {
        return MailCategory.builder()
                .mailCategoryName(mailCategoryName)
                .build();
    }
}
