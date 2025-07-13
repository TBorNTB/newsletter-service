package com.sejong.newsletterservice.application.exception;

import com.sejong.newsletterservice.application.email.NewsletterScheduler;
import com.sejong.newsletterservice.application.subscriber.SubscriberController;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("잘못된 요청입니다: " + ex.getMessage());
    }

    @ExceptionHandler(EmailSendException.class)
    public ResponseEntity<String> handleEmailError(EmailSendException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("이메일 전송 실패: "+ex.getMessage());
    }
}
