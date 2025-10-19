package com.sejong.newsletterservice.infrastructure.feign;

import com.sejong.newsletterservice.core.enums.TechCategory;
import com.sejong.newsletterservice.infrastructure.feign.response.ContentResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "elastic-service", path="/internal")
public interface ElasticServiceClient {

    @GetMapping("/weekly-popular") // 주간 인기글(프로젝트, 동아리 뉴스, cs 지식)
    ContentResponse getWeeklyPopularContent();

    @GetMapping("/interest-contents") // 구독자의 관심 분야 기술 문서(프로젝트, cs 지식)
    ContentResponse getInterestingContent(List<TechCategory> techCategories);
}