package com.sejong.newsletterservice.application.subscriber.dto.response;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.enums.TechCategory;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberStatusResponse {
    private String email;
    private boolean registered;
    private boolean active;
    private EmailFrequency emailFrequency;
    private List<TechCategory> selectedCategories;
    private String message;

    public static SubscriberStatusResponse notRegistered(String email) {
        return SubscriberStatusResponse.builder()
                .email(email)
                .registered(false)
                .active(false)
                .emailFrequency(null)
                .selectedCategories(List.of())
                .message("등록된 구독자가 없습니다.")
                .build();
    }

    public static SubscriberStatusResponse from(Subscriber subscriber) {
        boolean isActive = Boolean.TRUE.equals(subscriber.getActive());
        return SubscriberStatusResponse.builder()
                .email(subscriber.getEmail())
                .registered(true)
                .active(isActive)
                .emailFrequency(subscriber.getEmailFrequency())
                .selectedCategories(subscriber.getSubscribedTechCategories())
                .message(isActive ? "구독 중입니다." : "구독 해제 상태입니다.")
                .build();
    }
}
