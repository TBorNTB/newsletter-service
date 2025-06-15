package com.sejong.newsletterservice.api.controller.dto.request;

import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SubscribeRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private List<MailCategoryName> selectedCategories;

    @NotEmpty
    private Long emailFrequency;
}