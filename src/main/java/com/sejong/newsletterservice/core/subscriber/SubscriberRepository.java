package com.sejong.newsletterservice.core.subscriber;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
import java.util.List;
import java.util.Optional;

public interface SubscriberRepository {
    List<Subscriber> findByEmailFrequency(EmailFrequency frequency);

    List<Subscriber> findAll();

    Subscriber findOne(String email);

    Optional<Subscriber> findOptional(String email);


    Subscriber save(Subscriber subscriber);
}
