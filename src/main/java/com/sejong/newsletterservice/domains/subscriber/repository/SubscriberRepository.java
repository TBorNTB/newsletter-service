package com.sejong.newsletterservice.domains.subscriber.repository;

import com.sejong.newsletterservice.domains.subscriber.domain.Subscriber;
import com.sejong.newsletterservice.support.common.EmailFrequency;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    @Query("SELECT s FROM Subscriber s LEFT JOIN FETCH s.subscriberCategories sc LEFT JOIN FETCH sc.category WHERE s.email = :email")
    Optional<Subscriber> findByEmail(@Param("email") String email);

    @Query("SELECT DISTINCT s FROM Subscriber s LEFT JOIN FETCH s.subscriberCategories sc LEFT JOIN FETCH sc.category WHERE s.emailFrequency = :frequency AND s.active = true")
    List<Subscriber> findActiveByEmailFrequency(@Param("frequency") EmailFrequency frequency);
}
