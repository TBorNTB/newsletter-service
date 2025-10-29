package com.sejong.newsletterservice.core.mailgategory;

import com.sejong.newsletterservice.core.enums.TechCategory;
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
    private TechCategory techCategory;

    public static MailCategory of(TechCategory mailCategoryName) {
        return MailCategory.builder()
                .techCategory(mailCategoryName)
                .build();
    }
}
