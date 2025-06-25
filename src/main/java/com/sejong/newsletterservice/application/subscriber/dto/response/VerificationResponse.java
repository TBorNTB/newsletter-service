package com.sejong.newsletterservice.application.subscriber.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationResponse {
    private String email;
    private String message;

    public static VerificationResponse of(String email,String message){
        return new VerificationResponse(email, message);
    }
}
