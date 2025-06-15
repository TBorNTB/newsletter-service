package com.sejong.newsletterservice.domain.model;

import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MailCategory {
    private Long id;
    private Subscriber subscriber;
    private MailCategoryName mailCategoryName;
}
