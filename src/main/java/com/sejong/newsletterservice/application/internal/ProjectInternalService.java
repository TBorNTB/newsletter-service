package com.sejong.newsletterservice.application.internal;

import com.sejong.newsletterservice.core.error.code.ErrorCode;
import com.sejong.newsletterservice.core.error.exception.ApiException;
import com.sejong.newsletterservice.infrastructure.feign.ArchiveClient;
import com.sejong.newsletterservice.infrastructure.feign.ProjectClient;
import com.sejong.newsletterservice.infrastructure.feign.response.ArchiveResponse;
import com.sejong.newsletterservice.infrastructure.feign.response.ProjectResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProjectInternalService {
    private final ProjectClient projectClient;

    @CircuitBreaker(name = "myFeignClient", fallbackMethod = "getFavoritePostFallback")
    public ProjectResponse getFavoritePost() {
        ResponseEntity<ProjectResponse> response = projectClient.getFavoriteProject();

        if (response.getBody()==null) {
            log.info("Project 검증 실패");
            throw new ApiException(ErrorCode.BAD_REQUEST,"해당 Project에 인기 프로젝트가 없습니다.");
        }

        return response.getBody();
    }

    private ProjectResponse getFavoritePostFallback( Throwable t) {
        String message = t.getMessage().startsWith("해당") ? t.getMessage() : "Project Server 에러";
        throw new ApiException(ErrorCode.External_Server_Error, message);
    }
}
