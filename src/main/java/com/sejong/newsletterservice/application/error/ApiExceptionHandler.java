package com.sejong.newsletterservice.application.error;


import com.sejong.newsletterservice.application.error.api.ErrorResponse;
import com.sejong.newsletterservice.core.error.code.ErrorCode;
import com.sejong.newsletterservice.core.error.code.ErrorCodeIfs;
import com.sejong.newsletterservice.core.error.exception.ApiException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
@Order(value=Integer.MIN_VALUE)
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Object> apiException(
            ApiException apiException
    ){
        ErrorCodeIfs errorCode = apiException.getErrorCodeIfs();
        ErrorResponse errorResponse = ErrorResponse.ERROR(errorCode, apiException.getMessage());
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Object> illegalArgument(IllegalArgumentException ex) {
        ErrorResponse errorResponse = ErrorResponse.ERROR(ErrorCode.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(ErrorCode.BAD_REQUEST.getHttpStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<Object> illegalState(IllegalStateException ex) {
        ErrorResponse errorResponse = ErrorResponse.ERROR(ErrorCode.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(ErrorCode.BAD_REQUEST.getHttpStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> unknown(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.ERROR(ErrorCode.SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(ErrorCode.SERVER_ERROR.getHttpStatusCode()).body(errorResponse);
    }
}
