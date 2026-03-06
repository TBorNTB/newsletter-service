package com.sejong.newsletterservice.domains.subscriber.dto.request;

import com.sejong.newsletterservice.support.common.EmailFrequency;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequest {

    @Email
    @NotEmpty
    private String email;

    @NotNull
    private EmailFrequency emailFrequency;

    @NotNull
    @NotEmpty
    private List<String> selectedCategories;

    @NotNull
    private Boolean chasingPopularity;

    public SubscriberRequestVO toVO(String code) {
        return new SubscriberRequestVO(email, emailFrequency, selectedCategories, chasingPopularity, code);
    }
}
