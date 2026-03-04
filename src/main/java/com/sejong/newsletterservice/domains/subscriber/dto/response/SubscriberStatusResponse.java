package com.sejong.newsletterservice.domains.subscriber.dto.response;

import com.sejong.newsletterservice.domains.subscriber.domain.Subscriber;
import com.sejong.newsletterservice.support.common.EmailFrequency;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberStatusResponse {
    private String email;
    private boolean registered;
    private boolean active;
    private EmailFrequency emailFrequency;
    private List<String> selectedCategories;
    private boolean chasingPopularity;
    private String message;

    public static SubscriberStatusResponse from(Subscriber subscriber) {
        boolean isActive = Boolean.TRUE.equals(subscriber.getActive());
        return SubscriberStatusResponse.builder()
                .email(subscriber.getEmail())
                .registered(true)
                .active(isActive)
                .emailFrequency(subscriber.getEmailFrequency())
                .selectedCategories(subscriber.getCategoryNames())
                .chasingPopularity(Boolean.TRUE.equals(subscriber.getChasingPopularity()))
                .message(isActive ? "구독 중입니다." : "구독 해제 상태입니다.")
                .build();
    }

    public static SubscriberStatusResponse notRegistered(String email) {
        return SubscriberStatusResponse.builder()
                .email(email)
                .registered(false)
                .active(false)
                .selectedCategories(List.of())
                .message("등록된 구독자가 없습니다.")
                .build();
    }
}
