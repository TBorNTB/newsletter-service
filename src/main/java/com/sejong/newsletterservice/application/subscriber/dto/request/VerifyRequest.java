package com.sejong.newsletterservice.application.subscriber.dto.request;

import lombok.Data;

@Data
public class VerifyRequest {
    private String email;
    private String code;
}
