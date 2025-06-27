package com.sejong.newsletterservice.core.subscriber;

import com.sejong.newsletterservice.core.common.RandomProvider;
import com.sejong.newsletterservice.core.csknowledge.CsKnowledge;
import com.sejong.newsletterservice.core.csknowledge.CsKnowledgeRepository;
import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.enums.MailCategoryName;
import com.sejong.newsletterservice.core.mailgategory.MailCategory;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.fixture.SubscriberFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriberTest {

    @Mock
    CsKnowledgeRepository csKnowledgeRepository;

    @Mock
    RandomProvider randomProvider;

    @Mock
    Random mockRandom;

    @Test
    void of_정상생성() {
        Subscriber subscriber = Subscriber.of("test@example.com", EmailFrequency.DAILY, LocalDateTime.of(2024, 1, 1, 0, 0));

        assertThat(subscriber.getEmail()).isEqualTo("test@example.com");
        assertThat(subscriber.getEmailFrequency()).isEqualTo(EmailFrequency.DAILY);
        assertThat(subscriber.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 1, 1, 0, 0));
    }

    @Test
    void from_정상생성() {
        SubscriberRequestVO vo = new SubscriberRequestVO("test@example.com", EmailFrequency.WEEKLY, List.of(MailCategoryName.CRYPTOGRAPHY), "code123");

        Subscriber subscriber = Subscriber.from(vo, LocalDateTime.of(2024, 5, 5, 12, 0));

        assertThat(subscriber.getEmail()).isEqualTo("test@example.com");
        assertThat(subscriber.getEmailFrequency()).isEqualTo(EmailFrequency.WEEKLY);
    }

    @Test
    void pickNextKnowledgeToSend_카테고리_탐색_성공() {
        // given
        Subscriber subscriber = SubscriberFixture.구독자_카테고리_포함();

        CsKnowledge knowledge = mock(CsKnowledge.class);

        when(randomProvider.getRandom()).thenReturn(mockRandom);
        when(csKnowledgeRepository.findUnsentKnowledge(eq(MailCategoryName.DIGITAL_FORENSICS), eq("user@example.com")))
                .thenReturn(Optional.of(knowledge));

        // when
        Optional<CsKnowledge> result = subscriber.pickNextKnowledgeToSend(csKnowledgeRepository, randomProvider);

        // then
        assertThat(result).isPresent();
        assertThat(result.get()).isSameAs(knowledge);
    }

    @Test
    void pickNextKnowledgeToSend_모든카테고리실패_빈값() {
        // given
        Subscriber subscriber = SubscriberFixture.구독자_카테고리_포함();

        when(randomProvider.getRandom()).thenReturn(mockRandom);
        when(csKnowledgeRepository.findUnsentKnowledge(any(), eq("user@example.com"))).thenReturn(Optional.empty());

        // when
        Optional<CsKnowledge> result = subscriber.pickNextKnowledgeToSend(csKnowledgeRepository, randomProvider);

        // then
        assertThat(result).isEmpty();
    }
}
