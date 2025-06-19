package com.sejong.newsletterservice.application.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationService {
  private final Map<String,String> codeStorage = new HashMap<>();

    public String generateAndStoreCode(String email) {
        String code = String.format("%06d", new Random().nextInt(999999));
        codeStorage.put(email,code);
        return code;
    }

    public boolean verifyEmailCode(String email, String code) {
        return codeStorage.get(email).equals(code);
    }
}
