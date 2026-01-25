package com.sejong.newsletterservice.application.subscriber.dto.request;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.enums.TechCategory;
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
public class UpdatePreferencesVerifyRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String code;

    @NotNull
    private EmailFrequency emailFrequency;

    @NotNull
    @NotEmpty
    private List<TechCategory> selectedCategories;

    public UpdateSubscriptionRequest toUpdateRequest() {
        return new UpdateSubscriptionRequest(email, emailFrequency, selectedCategories);
    }
}
