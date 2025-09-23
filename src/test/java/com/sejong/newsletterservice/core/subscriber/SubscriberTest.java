package com.sejong.newsletterservice.core.subscriber;

import static org.assertj.core.api.Assertions.assertThat;

import com.sejong.newsletterservice.core.common.RandomProvider;
import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.enums.TechCategory;
import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubscriberTest {

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
        SubscriberRequestVO vo = new SubscriberRequestVO("test@example.com", EmailFrequency.WEEKLY, List.of(
                TechCategory.CRYPTOGRAPHY), "code123");

        Subscriber subscriber = Subscriber.from(vo, LocalDateTime.of(2024, 5, 5, 12, 0));

        assertThat(subscriber.getEmail()).isEqualTo("test@example.com");
        assertThat(subscriber.getEmailFrequency()).isEqualTo(EmailFrequency.WEEKLY);
    }
}
