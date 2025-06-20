package com.sejong.newsletterservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Sample API",
                description = "Swagger 테스트.",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig{

    @Bean
    public GroupedOpenApi SampleUserOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("com.example")
                .pathsToMatch(paths)
                .build();
    }
}