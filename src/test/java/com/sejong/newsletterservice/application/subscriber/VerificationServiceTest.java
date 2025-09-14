package com.sejong.newsletterservice.application.subscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.sejong.newsletterservice.application.email.VerificationEmailSender;
import com.sejong.newsletterservice.application.subscriber.dto.response.VerificationResponse;
import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.enums.TechCategory;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.fixture.SubscriberRequestFixture;
import com.sejong.newsletterservice.infrastructure.redis.SubscriberCacheService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class VerificationServiceTest {

    private VerificationService verificationService;
    @Mock
    private VerificationEmailSender verificationEmailSender;
    @Mock
    private SubscriberCacheService subscriberCacheService;

    @BeforeEach
    void setUp() {
        verificationService = new VerificationService(verificationEmailSender, subscriberCacheService);
    }

    @Test
    void generateCode는_항상_6자리_숫자_문자열을_반환한다() {
        // when
        String code = verificationService.generateCode();

        // then
        assertNotNull(code, "코드는 null이 아니어야 한다.");
        assertEquals(6, code.length(), "코드는 6자리어야 한다.");
        assertTrue(code.matches("\\d{6}"), "코드는 숫자만 포함해야 한다.");

        int numericCode = Integer.parseInt(code);
        assertTrue(numericCode >= 100000 && numericCode <= 999999, "코드의 범위는 100000이상 999999이하");
    }

    @Test
    void generateCode를_여러번_호출해도_형식은_일정하다() {
        for (int i = 0; i < 1000; i++) {
            String code = verificationService.generateCode();
            assertEquals(6, code.length());
            assertTrue(code.matches("\\d{6}"));
        }
    }

    @Test
    void 인증번호를_전송한다() {
        // given
        SubscriberRequestVO requestVO = SubscriberRequestFixture.기본_요청_vo();
        when(verificationEmailSender.send(requestVO.email(), requestVO.code())).thenReturn("user@example.com");

        // when
        VerificationResponse verificationResponse = verificationService.sendVerification(requestVO);

        // then
        assertThat(verificationResponse.getEmail()).isEqualTo("user@example.com");
        assertThat(verificationResponse.getMessage()).isEqualTo("이메일이 성공적으로 전송되었습니다.");
    }

    @Test
    void 인증코드를_검증한다() {
        // given
        String email = "user@example.com";
        String inputCode = "123456";
        SubscriberRequestVO subscriberRequestVO = SubscriberRequestFixture.기본_요청_vo();
        when(subscriberCacheService.getEmailInfo(email)).thenReturn(Optional.of(subscriberRequestVO));

        // when
        SubscriberRequestVO requestVO = verificationService.verifyEmailCode(email, inputCode);

        // then
        assertThat(requestVO.email()).isEqualTo("user@example.com");
        assertThat(requestVO.code()).isEqualTo("123456");
    }

    @Test
    void 저장된_이메일이_없으면_오류를_반환한다() {
       // given
       String email = "unknown@example.com";
       String inputCode = "123456";

       // when
       when(subscriberCacheService.getEmailInfo(email)).thenReturn(Optional.empty());

       // then
        assertThatThrownBy(()->verificationService.verifyEmailCode(email, inputCode))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("인증 정보 없음 또는 코드 불일치: " + email);
    }
    public SubscriberRequestVO verifyEmailCode(String email, String inputCode) {
        return subscriberCacheService.getEmailInfo(email)
                .filter(info -> info.code().equals(inputCode))
                .orElseThrow(() -> new IllegalArgumentException("인증 정보 없음 또는 코드 불일치: " + email));
    }

    @Test
    void 인증코드가_일치하지_않으면_오류를_반환한다() {
        // given
        String email = "user@example.com";
        String inputCode = "123456";
        String storeCode = "999999";

        SubscriberRequestVO storedVO = new SubscriberRequestVO(
                email,
                EmailFrequency.DAILY,
                List.of(TechCategory.CRYPTOGRAPHY),
                storeCode
        );
        when(subscriberCacheService.getEmailInfo(email)).thenReturn(Optional.of(storedVO));

        // when && then
        assertThatThrownBy(()->verificationService.verifyEmailCode(email, inputCode))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("인증 정보 없음 또는 코드 불일치: " + email);
    }

}