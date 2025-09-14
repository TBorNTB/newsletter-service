package com.sejong.newsletterservice.infrastructure.feign;

import com.sejong.newsletterservice.infrastructure.feign.response.MetaVisitersResponse;
import com.sejong.newsletterservice.infrastructure.feign.response.MetaVisitersAllResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name ="meta-service", path="/internal/meta")
public interface MetaClient {
    @GetMapping("/news/most-visiter")
    ResponseEntity<MetaVisitersResponse> mostVisitersNews();

    @GetMapping("/archive/most-visiter")
    ResponseEntity<MetaVisitersResponse> mostVisitersArchive();

    @GetMapping("/project/most-visiter")
    ResponseEntity<MetaVisitersResponse> mostVisitersProject();

    @GetMapping("/most-visiters")
    ResponseEntity<MetaVisitersAllResponse> mostVisitersAll();

}
