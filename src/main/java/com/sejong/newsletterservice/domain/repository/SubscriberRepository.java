package com.sejong.newsletterservice.domain.repository;

import com.sejong.newsletterservice.domain.model.Subscriber;
import com.sejong.newsletterservice.domain.model.enums.EmailFrequency;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository {
    List<Subscriber> findByEmailFrequency(EmailFrequency frequency);
}
