package com.sejong.newsletterservice.core.util;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class RandomProvider {
    private static SecureRandom random = new SecureRandom();

    public static String generateRandomCode(int length) {
        int upperLimit = (int) Math.pow(10, length);
        return String.format("%0" + length + "d", random.nextInt(upperLimit));
    }
}
