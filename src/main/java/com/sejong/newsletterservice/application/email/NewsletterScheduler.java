package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
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

    @Scheduled(cron = "58 53 16 * * *", zone = "Asia/Seoul")
    public void sendDailyNewsletter() {
        log.info(" 매일 오전 7시 뉴스레터 전송 시작");
        Long sentLogCount = newsletterService.sendPopularContents(EmailFrequency.WEEKLY);
        log.info(" 뉴스레터 전송 완료 기록된 로그 수 : {}",sentLogCount);
    }

    @Scheduled(cron = "0 1 5 * * *", zone = "Asia/Seoul")
    public void sendWeeklyNewsletter() {
        log.info(" 매일 오후 3시 뉴스레터 전송 시작");
        Long sentLogCount = newsletterService.sendInterestingContents(EmailFrequency.WEEKLY);
        log.info(" 뉴스레터 전송 완료 기록된 로그 수 : {}",sentLogCount);
    }

    @GetMapping("/test-mail")
    public String testMail() {
        log.info(" 매일 오전 7시 뉴스레터 전송 시작");
        newsletterService.sendPopularContents(EmailFrequency.DAILY);
        log.info(" 뉴스레터 전송 완료");
        return "메일 전송 완료";
    }
}
