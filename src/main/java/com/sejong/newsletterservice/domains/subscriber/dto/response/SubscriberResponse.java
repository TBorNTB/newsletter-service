package com.sejong.newsletterservice.domains.subscriber.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberResponse {
    private String email;
    private String message;

    public static SubscriberResponse registered(String email) {
        return new SubscriberResponse(email, "구독이 정상적으로 완료되었습니다.");
    }

    public static SubscriberResponse cancelled(String email) {
        return new SubscriberResponse(email, "구독 해제가 정상적으로 완료되었습니다.");
    }

    public static SubscriberResponse updated(String email) {
        return new SubscriberResponse(email, "구독 설정이 변경되었습니다.");
    }

    public static SubscriberResponse verified(String email) {
        return new SubscriberResponse(email, "메일이 인증 되었습니다.");
    }
}
