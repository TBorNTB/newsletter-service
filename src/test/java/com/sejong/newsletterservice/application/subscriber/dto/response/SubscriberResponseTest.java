package com.sejong.newsletterservice.application.subscriber.dto.response;

import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.fixture.SubscriberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SubscriberResponseTest {

    @DisplayName("Subscriber를 이용하여 SubscriberResponse를 만드는 정적팩터리 메서드를 테스트 한다")
    @Test
    void SubscriberResponse_정적팩터리_메서드는_정상_작동한다() {
        // given
        Subscriber subscriber = SubscriberFixture.기본_구독자();

        // when
        SubscriberResponse response = SubscriberResponse.from(subscriber);

        // then
        assertThat(response.getEmail()).isEqualTo(subscriber.getEmail());
        assertThat(response.getMessage()).isEqualTo("구독이 정상적으로 완료되었습니다.");
    }
}