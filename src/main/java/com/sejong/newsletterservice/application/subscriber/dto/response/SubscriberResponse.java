package com.sejong.newsletterservice.application.subscriber.dto.response;

import com.sejong.newsletterservice.core.subscriber.Subscriber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberResponse {
    private String email;
    private String message;

    public static SubscriberResponse from(Subscriber subscriber) {
        return SubscriberResponse.builder()
                .email(subscriber.getEmail())
                .message("구독이 정상적으로 완료되었습니다.")
                .build();
    }

    public static SubscriberResponse deletedFrom(Subscriber subscriber) {
        return SubscriberResponse.builder()
                .email(subscriber.getEmail())
                .message("구독 해제가 정상적으로 완료되었습니다.")
                .build();
    }

    public static SubscriberResponse updatedFrom(Subscriber subscriber) {
        return SubscriberResponse.builder()
                .email(subscriber.getEmail())
                .message("구독 설정이 변경되었습니다.")
                .build();
    }

    public static SubscriberResponse from(String email) {
        return SubscriberResponse.builder()
                .email(email)
                .message("구독 설정이 변경되었습니다.")
                .build();
    }
}
