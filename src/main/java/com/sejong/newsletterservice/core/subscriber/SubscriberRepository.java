package com.sejong.newsletterservice.core.subscriber;

import com.sejong.newsletterservice.core.enums.EmailFrequency;

import java.util.List;

public interface SubscriberRepository {
    List<Subscriber> findByEmailFrequency(EmailFrequency frequency);
}
