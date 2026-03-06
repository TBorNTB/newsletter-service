package com.sejong.newsletterservice.domains.subscriber.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationResponse {
    private String email;
    private String message;

    public static VerificationResponse of(String email, String message) {
        return new VerificationResponse(email, message);
    }
}
