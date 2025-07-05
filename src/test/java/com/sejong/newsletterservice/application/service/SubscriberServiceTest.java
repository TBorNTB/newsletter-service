package com.sejong.newsletterservice.application.service;

import com.sejong.newsletterservice.application.subscriber.dto.response.SubscriberResponse;
import com.sejong.newsletterservice.application.subscriber.SubscriberService;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.fake.FakeMailCategoryRepository;
import com.sejong.newsletterservice.fake.FakeSubscriberRepository;
import com.sejong.newsletterservice.fixture.SubscriberRequestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {
    private SubscriberService subscriberService;
    private FakeSubscriberRepository fakeSubscriberRepository;
    private FakeMailCategoryRepository fakeMailCategoryRepository;

    private String CODE = "333333";

    @BeforeEach
    void setUp() {
        fakeSubscriberRepository = new FakeSubscriberRepository();
        fakeMailCategoryRepository = new FakeMailCategoryRepository();
        subscriberService = new SubscriberService(fakeMailCategoryRepository);
    }

    @Test
    void 구독자를_정상적으로_저장한다() {
        // given
        SubscriberRequestVO request = SubscriberRequestFixture.기본_요청_vo();

        // when
        SubscriberResponse response = subscriberService.register(request);

        // then
        assertThat(response.getEmail()).isEqualTo("user@example.com");
    }

    @Test
    void 이메일이_없으면_예외가_발생한다() {
        // expect
        assertThrows(IllegalArgumentException.class, SubscriberRequestFixture::이메일_없음_vo);
    }

    @Test
    void 카테고리를_선택하지_않으면_예외가_발생한다() {

        // expect
        assertThrows(IllegalArgumentException.class, SubscriberRequestFixture::카테고리_없음_vo);
    }
}