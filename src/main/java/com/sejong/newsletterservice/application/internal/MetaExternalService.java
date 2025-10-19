package com.sejong.newsletterservice.application.internal;

import com.sejong.newsletterservice.core.error.code.ErrorCode;
import com.sejong.newsletterservice.core.error.exception.ApiException;
import com.sejong.newsletterservice.infrastructure.feign.MetaClient;
import com.sejong.newsletterservice.infrastructure.feign.response.MetaVisitersResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MetaExternalService {

    private final MetaClient metaClient;

    @CircuitBreaker(name = "myFeignClient", fallbackMethod = "getFavoritePostFallback")
    public MetaVisitersResponse getMostVisitersNews() {
        ResponseEntity<MetaVisitersResponse> response = metaClient.mostVisitersNews();
        log.info("response: {}",response.getBody());
        if (response.getBody()==null) {
            log.info("News 검증 실패");
            throw new ApiException(ErrorCode.BAD_REQUEST,"해당 News에 인기 게시글이 없습니다.");
        }

        return response.getBody();
    }

    private MetaVisitersResponse getMostVisitersNewsFallback(Throwable t) {
        log.info("fallback method is called.");
        if( t instanceof ApiException){
            throw (ApiException) t;
        }
        throw new ApiException(ErrorCode.EXTERNAL_SERVER_ERROR, "잠시 서비스 이용이 불가합니다.");
    }

    @CircuitBreaker(name = "myFeignClient", fallbackMethod = "getMostVisitersArchiveFallback")
    public MetaVisitersResponse getMostVisitersArchive() {
        ResponseEntity<MetaVisitersResponse> response = metaClient.mostVisitersArchive();
        log.info("response: {}",response.getBody());
        if (response.getBody()==null) {
            log.info("News 검증 실패");
            throw new ApiException(ErrorCode.BAD_REQUEST,"해당 News에 인기 게시글이 없습니다.");
        }

        return response.getBody();
    }

    private MetaVisitersResponse getMostVisitersArchiveFallback(Throwable t) {
        log.info("fallback method is called.");
        if( t instanceof ApiException){
            throw (ApiException) t;
        }
        throw new ApiException(ErrorCode.EXTERNAL_SERVER_ERROR, "잠시 서비스 이용이 불가합니다.");
    }

    @CircuitBreaker(name = "myFeignClient", fallbackMethod = "getMostVisitersProjectFallback")
    public MetaVisitersResponse getMostVisitersProject() {
        ResponseEntity<MetaVisitersResponse> response = metaClient.mostVisitersProject();
        log.info("response: {}",response.getBody());
        if (response.getBody()==null) {
            log.info("News 검증 실패");
            throw new ApiException(ErrorCode.BAD_REQUEST,"해당 News에 인기 게시글이 없습니다.");
        }

        return response.getBody();
    }

    private MetaVisitersResponse getMostVisitersProjectFallback(Throwable t) {
        log.info("fallback method is called.");
        if( t instanceof ApiException){
            throw (ApiException) t;
        }
        throw new ApiException(ErrorCode.EXTERNAL_SERVER_ERROR, "잠시 서비스 이용이 불가합니다.");
    }
}
