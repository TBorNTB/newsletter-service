package com.sejong.newsletterservice.core.common;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RandomProviderTest {
    RandomProvider randomProvider = new RandomProvider();

    @Test
    void nextInt_결과가_범위_내에_포함되는지_확인() {
        int bound = 10;

        for (int i = 0; i < 100; i++) {
            int result = randomProvider.nextInt(bound);
            assertThat(result).isGreaterThanOrEqualTo(0).isLessThan(bound);
        }
    }

    @Test
    void getRandom_호출시_객체_생성되는지_확인() {
        Random random = randomProvider.getRandom();
        assertThat(random).isNotNull();

        int result = random.nextInt(5);
        assertThat(result).isBetween(0, 4);
    }
}