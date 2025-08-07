package com.sejong.newsletterservice.core.error.code;

public interface ErrorCodeIfs {
    Integer getHttpStatusCode();
    Integer getErrorCode();
    String getDescription();
}
