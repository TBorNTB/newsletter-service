package com.sejong.newsletterservice.domain.model.vo;

import com.sejong.newsletterservice.domain.model.enums.EmailFrequency;
import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;
import lombok.Data;
import lombok.Getter;

import java.util.List;

public record SubscriberRequestVO(
        String email,
        EmailFrequency emailFrequency,
        List<MailCategoryName> selectedCategories
) {
    public SubscriberRequestVO {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 반드시 입력해야 됩니다.");
        }
        if (selectedCategories == null || selectedCategories.isEmpty()) {
            throw new IllegalArgumentException("category는 반드시 하나는 선택해야 됩니다.");
        }
    }
}
