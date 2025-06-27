package com.sejong.newsletterservice.core.subscriber;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository {
    List<Subscriber> findByEmailFrequency(EmailFrequency frequency);
}
