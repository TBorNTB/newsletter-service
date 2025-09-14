package com.sejong.newsletterservice.infrastructure.feign;

import com.sejong.newsletterservice.infrastructure.feign.response.ProjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name ="project-service", path="/internal/project")
public interface ProjectClient {
    @GetMapping("/favorite-post")
    ResponseEntity<ProjectResponse> getFavoriteProject();
}
