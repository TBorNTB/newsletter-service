package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.core.common.RandomProvider;
import com.sejong.newsletterservice.core.csknowledge.CsKnowledge;
import com.sejong.newsletterservice.core.csknowledge.CsKnowledgeRepository;
import com.sejong.newsletterservice.core.sentlog.SentLog;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.fixture.CsKnowledgeFixture;
import com.sejong.newsletterservice.fixture.SubscriberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsletterDomainServiceTest {
    private NewsletterDomainService newsletterDomainService;

    @Mock
    private CsKnowledgeRepository csKnowledgeRepository;
    @Mock
    private NewsletterEmailSender newsletterEmailSender;
    @Mock
    private RandomProvider randomProvider;

    @BeforeEach
    void setUp() {
        newsletterDomainService = new NewsletterDomainService(csKnowledgeRepository, newsletterEmailSender, randomProvider);

    }

    @Test
    void CS_지식이_있으면_로그를_반환한다() {
        // given
        Subscriber mockSubscriber = mock(Subscriber.class);  // 🎯 목 객체 사용
        CsKnowledge csKnowledge = CsKnowledgeFixture.기본_지식();

        when(mockSubscriber.pickNextKnowledgeToSend(csKnowledgeRepository, randomProvider))
                .thenReturn(Optional.of(csKnowledge));
        when(mockSubscriber.getEmail()).thenReturn("test@example.com");

        // when
        Optional<SentLog> sentLog = newsletterDomainService.sendNewsletterTo(mockSubscriber);

        // then
        assertThat(sentLog).isPresent();
        assertThat(sentLog.get().getEmail()).isEqualTo("test@example.com");
        assertThat(sentLog.get().getCsKnowledgeId()).isEqualTo(csKnowledge.getId());
        verify(newsletterEmailSender).send(
                mockSubscriber.getEmail(),
                csKnowledge.getTitle(),
                csKnowledge.getId()
        );
    }

    @Test
    void Cs_지식이_없으면_로그는_생성되지_않는다() {
        // given
        Subscriber mockSubscriber = mock(Subscriber.class);
        String email = "test@example.com";
        when(mockSubscriber.pickNextKnowledgeToSend(csKnowledgeRepository, randomProvider))
                .thenReturn(Optional.empty());
        when(mockSubscriber.getEmail()).thenReturn(email);

        // when
        Optional<SentLog> sentLog = newsletterDomainService.sendNewsletterTo(mockSubscriber);

        // then
        assertThat(sentLog).isEmpty();
        verify(newsletterEmailSender).send(email, "<*>[뉴스레터] 지식이 없습니다 😢", null);
    }
}