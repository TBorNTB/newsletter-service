package com.sejong.newsletterservice.support.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> apiException(ApiException ex) {
        ErrorCodeIfs errorCode = ex.getErrorCodeIfs();
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(ErrorResponse.of(errorCode, ex.getErrorDescription()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(ErrorCode.BAD_REQUEST.getHttpStatusCode())
                .body(ErrorResponse.of(ErrorCode.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> illegalState(IllegalStateException ex) {
        return ResponseEntity
                .status(ErrorCode.BAD_REQUEST.getHttpStatusCode())
                .body(ErrorResponse.of(ErrorCode.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unknown(Exception ex) {
        return ResponseEntity
                .status(ErrorCode.SERVER_ERROR.getHttpStatusCode())
                .body(ErrorResponse.of(ErrorCode.SERVER_ERROR, ex.getMessage()));
    }
}
