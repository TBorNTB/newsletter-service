package com.sejong.newsletterservice.application.subscriber.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VerificationResponseTest {

    @DisplayName("VerificationResponse 정적팩터리 메서드 테스트")
    @Test
    void VerificationResponse의_정적팩터리_메서드는_정상_작동한다() {
        // given
        String email = "user@example.com";
        String message = "인증코드가 검증 되었습니다.";

        // when
        VerificationResponse response = VerificationResponse.of(email, message);

        // then
        assertThat(response.getEmail()).isEqualTo(email);
        assertThat(response.getMessage()).isEqualTo(message);
    }
}