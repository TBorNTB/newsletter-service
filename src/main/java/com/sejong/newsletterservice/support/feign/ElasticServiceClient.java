package com.sejong.newsletterservice.support.feign;

import com.sejong.newsletterservice.support.feign.response.ContentResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "elastic-service", path = "/internal")
public interface ElasticServiceClient {

    @GetMapping("/weekly-popular")
    ContentResponse getWeeklyPopularContent();

    @GetMapping("/interest-contents")
    List<ContentResponse> getInterestingContent(List<String> categoryNames);
}
