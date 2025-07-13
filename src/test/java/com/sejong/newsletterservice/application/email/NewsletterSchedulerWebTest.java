package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.application.config.MockBeansConfig;
import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.sentlog.SentLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NewsletterScheduler.class)  // 웹 계층 슬라이스 테스트
@AutoConfigureMockMvc
@Import(MockBeansConfig.class)
class NewsletterSchedulerWebTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    NewsletterService newsletterService;
    @Autowired
    SentLogFlusher sentLogFlusher;

    @Test
    void testMail_요청시_성공메시지를_반환한다() throws Exception {
        // when
        var result = mockMvc.perform(get("/test-mail"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string("메일 전송 완료"));
        verify(newsletterService).sendNewsletters(EmailFrequency.DAILY);
    }

    @Test
    void testLogMail_요청시_로그저장_메시지를_반환한다() throws Exception {
        // when
        var result = mockMvc.perform(get("/test-log-mail"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string("로그저장 완료"));
        verify(sentLogFlusher).flushSuccessLogsToDatabase();
    }
}
