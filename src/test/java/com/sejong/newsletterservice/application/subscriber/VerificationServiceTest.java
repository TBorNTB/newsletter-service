package com.sejong.newsletterservice.application.subscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
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
    void 인증번호를_전송한다() {
        // given
        SubscriberRequestVO requestVO = SubscriberRequestFixture.기본_요청_vo();

        // when
        VerificationResponse verificationResponse = verificationService.sendVerification(requestVO);

        // then
        assertThat(verificationResponse.getEmail()).isEqualTo("user@example.com");
        assertThat(verificationResponse.getMessage()).isEqualTo("인증코드가 이메일로 전송되었습니다.");

        verify(subscriberCacheService).save(requestVO);
        verify(verificationEmailSender).send(requestVO.email(), requestVO.code());
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
            false,
            storeCode
        );
        when(subscriberCacheService.getEmailInfo(email)).thenReturn(Optional.of(storedVO));

        // when && then
        assertThatThrownBy(()->verificationService.verifyEmailCode(email, inputCode))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("인증 정보 없음 또는 코드 불일치: " + email);
    }

}