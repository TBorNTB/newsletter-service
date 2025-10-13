package com.sejong.newsletterservice.application.subscriber.dto.request;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.enums.TechCategory;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
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
public class SubscriberRequest {

    @Email
    @NotEmpty
    private String email;

    @NotNull
    private EmailFrequency emailFrequency;

    @NotNull
    private List<TechCategory> selectedCategories;

    @NotNull
    private Boolean chasingPopularity;

    public SubscriberRequestVO toVO( String code) {
        return new SubscriberRequestVO(email, emailFrequency, selectedCategories, chasingPopularity, code);
    }
}