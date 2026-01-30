package com.sejong.newsletterservice.application.subscriber.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriberCancelResponse {
    private String email;
    private String message;

    public static SubscriberResponse of(String email) {
        return SubscriberResponse.builder()
                .email(email)
                .message("구독이 해제 되었습니다.")
                .build();
    }
}
