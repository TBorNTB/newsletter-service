package com.sejong.newsletterservice.application.subscriber.dto.request;

import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.fixture.SubscriberRequestFixture;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SubscriberRequestTest {

    @DisplayName("SubscriberRequest.toVO()는 올바른 VO 객체를 반환한다.")
    @Test
    void toVO_정상변환() {
        // given
        SubscriberRequest subscriberRequest = SubscriberRequestFixture.기본_요청();
        String code ="123456";

        // when
        SubscriberRequestVO requestVO = subscriberRequest.toVO(code);

        // then
        assertThat(requestVO.email()).isEqualTo(subscriberRequest.getEmail());
        assertThat(requestVO.code()).isEqualTo(code);
        assertThat(requestVO.emailFrequency()).isEqualTo(subscriberRequest.getEmailFrequency());
    }
}