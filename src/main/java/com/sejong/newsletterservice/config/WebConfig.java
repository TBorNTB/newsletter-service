package com.sejong.newsletterservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 모든 경로
                        .allowedOrigins("*") // 모든 Origin 허용
                        .allowedMethods("*") // GET, POST, PUT, DELETE 등
                        .allowedHeaders("*") // 모든 헤더 허용
                        .allowCredentials(false); // 인증 정보 제외
            }
        };
    }
}