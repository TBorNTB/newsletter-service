package com.sejong.newsletterservice.application.internal;

import com.sejong.newsletterservice.core.error.code.ErrorCode;
import com.sejong.newsletterservice.core.error.exception.ApiException;
import com.sejong.newsletterservice.infrastructure.feign.ArchiveClient;
import com.sejong.newsletterservice.infrastructure.feign.response.ArchiveResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArchiveInternalService {
    private final ArchiveClient archiveClient;

    @CircuitBreaker(name = "myFeignClient", fallbackMethod = "getFavoritePostFallback")
    public ArchiveResponse getFavoritePost() {
        ResponseEntity<ArchiveResponse> response = archiveClient.getFavoriteArchive();
        log.info("response: {}",response.getBody());
        if (response.getBody()==null) {
            log.info("Archive 검증 실패");
            throw new ApiException(ErrorCode.BAD_REQUEST,"해당 Archive에 인기 게시글이 없습니다.");
        }

        return response.getBody();
    }

    private ArchiveResponse getFavoritePostFallback( Throwable t) {
        String message = t.getMessage().startsWith("해당") ? t.getMessage() : "Archive Server 에러";
        throw new ApiException(ErrorCode.External_Server_Error, message);
    }
}
