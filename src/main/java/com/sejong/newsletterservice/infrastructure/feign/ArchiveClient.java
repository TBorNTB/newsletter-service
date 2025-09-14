package com.sejong.newsletterservice.infrastructure.feign;

import com.sejong.newsletterservice.infrastructure.feign.response.ArchiveResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name ="archive-service", path="/internal/archive")
public interface ArchiveClient {
    @GetMapping("/favorite-post")
    ResponseEntity<ArchiveResponse> getFavoriteArchive();
}
