package com.sejong.newsletterservice.support.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    public static ErrorResponse of(ErrorCodeIfs errorCodeIfs, String description) {
        return ErrorResponse.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(description)
                .build();
    }
}
