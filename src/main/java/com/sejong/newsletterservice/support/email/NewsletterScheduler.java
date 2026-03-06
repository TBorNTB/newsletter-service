package com.sejong.newsletterservice.support.email;

import com.sejong.newsletterservice.support.common.EmailFrequency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Component
@RequiredArgsConstructor
@RestController
public class NewsletterScheduler {

    private final NewsletterService newsletterService;

    @Scheduled(cron = "0 0 15 * * FRI", zone = "Asia/Seoul")
    public void sendWeeklyPopular() {
        log.info("주간 인기글 뉴스레터 전송 시작");
        Long count = newsletterService.sendPopularContents(EmailFrequency.WEEKLY);
        log.info("주간 인기글 전송 완료. 전송 수: {}", count);
    }

    @Scheduled(cron = "0 0 11 * * FRI", zone = "Asia/Seoul")
    public void sendWeeklyInteresting() {
        log.info("관심 카테고리 뉴스레터 전송 시작");
        Long count = newsletterService.sendInterestingContents(EmailFrequency.WEEKLY);
        log.info("관심 카테고리 전송 완료. 전송 수: {}", count);
    }

    @GetMapping("/test-mail")
    public String testMail() {
        newsletterService.sendPopularContents(EmailFrequency.DAILY);
        return "메일 전송 완료";
    }
}
