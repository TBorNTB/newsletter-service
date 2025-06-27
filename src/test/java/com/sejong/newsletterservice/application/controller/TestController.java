package com.sejong.newsletterservice.application.controller;


import com.sejong.newsletterservice.application.exception.EmailSendException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/illegal-arg")
    public String throwIllegal() {
        throw new IllegalArgumentException("파라미터 오류");
    }

    @GetMapping("/email-error")
    public String throwEmailError() {
        throw new EmailSendException("SMTP 서버 오류");
    }
}