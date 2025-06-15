package com.sejong.newsletterservice.api.controller.dto.request;

import com.sejong.newsletterservice.domain.model.enums.EmailFrequency;
import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;
import com.sejong.newsletterservice.domain.model.vo.SubscriberRequestVO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SubscriberRequest {

    @Email
    @NotEmpty
    private String email;

    @NotNull
    private EmailFrequency emailFrequency;

    @NotNull
    private List<MailCategoryName> selectedCategories;

    public SubscriberRequestVO toVO() {
        return new SubscriberRequestVO(email, emailFrequency, selectedCategories);
    }
}