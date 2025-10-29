package com.sejong.newsletterservice.application.email;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NewsletterSchedulerTest {
    @Mock
    private NewsletterService newsletterService;
    @Mock
    private SentLogFlusher sentLogFlusher;
    private NewsletterScheduler scheduler;

    @BeforeEach
    void setUp() {
        scheduler = new NewsletterScheduler(newsletterService, sentLogFlusher);
    }

    @Test
    void sendDailyNewsletter_호출시_뉴스레터가_전송된다() {
        // when
        scheduler.sendDailyNewsletter();

        // then
        verify(newsletterService).sendPopularContents(EmailFrequency.DAILY);
    }

    @Test
    void storeSentLog_호출시_메모리에_있는_로그들이_저장된다() {
        // when
        scheduler.storeSentLog();

        // then
        verify(sentLogFlusher).flushSuccessLogsToDatabase();
    }

    @Test
    void testMail_호출시_메일전송_결과를_반환한다() {
        // when
        String result = scheduler.testMail();

        // then
        assertThat(result).isEqualTo("메일 전송 완료");
        verify(newsletterService).sendPopularContents(EmailFrequency.DAILY);
    }

    @Test
    void testLogMail_호출시_로그저장_결과를_반환한다() {
        // when
        String result = scheduler.testLogMail();

        // then
        assertThat(result).isEqualTo("로그저장 완료");
        verify(sentLogFlusher).flushSuccessLogsToDatabase();
    }
}