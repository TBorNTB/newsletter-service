package com.sejong.newsletterservice.infrastructure.subscriber;

import com.sejong.newsletterservice.core.enums.EmailFrequency;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataJpaSubscriberRepository extends JpaRepository<SubscriberEntity, Long> {

    @Query("SELECT s FROM SubscriberEntity s LEFT JOIN FETCH s.mailCategories WHERE s.emailFrequency = :emailFrequency and s.active = true")
    List<SubscriberEntity> findByEmailFrequency(@Param("emailFrequency") EmailFrequency emailFrequency);

    @Query("SELECT DISTINCT s FROM SubscriberEntity s LEFT JOIN FETCH s.mailCategories WHERE s.email = :email")
    Optional<SubscriberEntity> findByEmail(@Param("email") String email);
}
