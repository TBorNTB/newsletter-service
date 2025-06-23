package com.sejong.newsletterservice.application.scheduler;

import com.sejong.newsletterservice.application.service.NewsletterService;
import com.sejong.newsletterservice.domain.model.enums.EmailFrequency;
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

    @Scheduled(cron = "0 07 12 * * *", zone = "Asia/Seoul")
    public void sendDailyNewsletter() {
        log.info(" 매일 오전 7시 뉴스레터 전송 시작");
        newsletterService.sendNewsletters(EmailFrequency.DAILY);
        log.info(" 뉴스레터 전송 완료");
    }

    @GetMapping("/test-mail")
    public String testMail() {
        log.info(" 매일 오전 7시 뉴스레터 전송 시작");
        newsletterService.sendNewsletters(EmailFrequency.DAILY);
        log.info(" 뉴스레터 전송 완료");
        return "메일 전송 완료";
    }
}
