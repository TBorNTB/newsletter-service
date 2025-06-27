package com.sejong.newsletterservice.api.controller.dto.request;

import lombok.Data;

@Data
public class VerifyRequest {
    private String email;
    private String code;
}
