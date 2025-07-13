package com.sejong.newsletterservice.application.exception;

import com.sejong.newsletterservice.application.controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestController.class)
@Import(GlobalErrorHandler.class)
class GlobalErrorHandlerTest {
    
    @Autowired
    MockMvc mockMvc;

    @Test
    void IllegalArgumentException_처리() throws Exception {
        mockMvc.perform(get("/illegal-arg"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("잘못된 요청입니다: 파라미터 오류"));
    }

    @Test
    void EmailSendException_처리() throws Exception {
        mockMvc.perform(get("/email-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("이메일 전송 실패: SMTP 서버 오류"));
    }
}