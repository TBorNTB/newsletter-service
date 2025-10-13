package com.sejong.newsletterservice.infrastructure.feign;

import com.sejong.newsletterservice.infrastructure.feign.response.PopularContentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "elastic-service", path="/internal")
public interface ElasticServiceClient {

    @GetMapping("/weekly-popular")
    PopularContentResponse getWeeklyPopularContent();
}