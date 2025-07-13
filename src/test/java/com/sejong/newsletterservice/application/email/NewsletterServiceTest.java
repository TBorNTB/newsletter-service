package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.sentlog.SentLog;
import com.sejong.newsletterservice.core.subscriber.Subscriber;
import com.sejong.newsletterservice.core.subscriber.SubscriberRepository;
import com.sejong.newsletterservice.fixture.SubscriberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsletterServiceTest {

    private NewsletterService newsletterService;

    @Mock
    private  SubscriberRepository subscriberRepository;
    @Mock
    private  NewsletterDomainService newsletterDomainService;

    @BeforeEach
    void setUp() {
        newsletterService = new NewsletterService(subscriberRepository, newsletterDomainService);
    }

    @Test
    void 빈도수를_찾아서_newsletterDomainService를_호출한다() {
        // given
        Subscriber subscriber1 = SubscriberFixture.구독자_카테고리_포함(1L,"test1@example.com");
        Subscriber subscriber2 = SubscriberFixture.구독자_카테고리_포함(2L,"test2@example.com");
        SentLog mockSentLog = mock(SentLog.class);

        when(subscriberRepository.findByEmailFrequency(EmailFrequency.DAILY))
                .thenReturn(List.of(subscriber1, subscriber2));
        when(newsletterDomainService.sendNewsletterTo(subscriber1))
                .thenReturn(Optional.of(mockSentLog));
        when(newsletterDomainService.sendNewsletterTo(subscriber2))
                .thenReturn(Optional.of(mockSentLog));
        // when
        Long sentLogCount = newsletterService.sendNewsletters(EmailFrequency.DAILY);

        // then
        assertThat(sentLogCount).isEqualTo(2L);
        verify(newsletterDomainService).sendNewsletterTo(subscriber1);
        verify(newsletterDomainService).sendNewsletterTo(subscriber2);
        verifyNoMoreInteractions(newsletterDomainService);
    }
}
