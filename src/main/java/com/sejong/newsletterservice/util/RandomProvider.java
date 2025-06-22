package com.sejong.newsletterservice.util;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomProvider {
    public int nextInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    public Random getRandom() {
        return new Random();
    }
}
