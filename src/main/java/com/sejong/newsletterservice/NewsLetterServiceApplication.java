package com.sejong.newsletterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewsLetterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsLetterServiceApplication.class, args);
    }

}
