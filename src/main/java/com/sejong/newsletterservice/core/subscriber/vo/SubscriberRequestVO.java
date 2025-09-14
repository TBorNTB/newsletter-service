package com.sejong.newsletterservice.core.subscriber.vo;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sejong.newsletterservice.core.enums.EmailFrequency;

import com.sejong.newsletterservice.core.enums.TechCategory;
import java.io.Serializable;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
public record SubscriberRequestVO(
        String email,
        EmailFrequency emailFrequency,
        List<TechCategory> selectedCategories,
        String code
) implements Serializable {
    public SubscriberRequestVO {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일은 반드시 입력해야 됩니다.");
        }
        if (selectedCategories == null || selectedCategories.isEmpty()) {
            throw new IllegalArgumentException("category는 반드시 하나는 선택해야 됩니다.");
        }
    }
}