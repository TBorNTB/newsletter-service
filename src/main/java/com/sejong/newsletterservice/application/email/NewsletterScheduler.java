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
    private final SentLogFlusher sentLogFlusher;

    @Scheduled(cron = "0 0 7 * * *", zone = "Asia/Seoul")
    public void sendDailyNewsletter() {
        log.info(" 매일 오전 7시 뉴스레터 전송 시작");
        Long sentLogCount = newsletterService.sendNewsletters(EmailFrequency.DAILY);
        log.info(" 뉴스레터 전송 완료 기록된 로그 수 : {}",sentLogCount);
    }

    @Scheduled(cron = "0 5 7 * * *", zone = "Asia/Seoul")
    public void storeSentLog() {
        log.info("전송된 메일 기록을 데이터베이스에 저장 시작");
        sentLogFlusher.flushSuccessLogsToDatabase();
        log.info("로그 저장 완료");
    }

    @GetMapping("/test-mail")
    public String testMail() {
        log.info(" 매일 오전 7시 뉴스레터 전송 시작");
        newsletterService.sendNewsletters(EmailFrequency.DAILY);
        log.info(" 뉴스레터 전송 완료");
        return "메일 전송 완료";
    }

    @GetMapping("/test-log-mail")
    public String testLogMail() {
        log.info("로그 저장 시작");
        sentLogFlusher.flushSuccessLogsToDatabase();
        log.info("로그 저장 완료");
        return "로그저장 완료";
    }
}
