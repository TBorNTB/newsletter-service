package com.sejong.newsletterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableRetry
@EnableDiscoveryClient
public class NewsLetterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsLetterServiceApplication.class, args);
    }

}
