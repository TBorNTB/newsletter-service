package com.sejong.newsletterservice.infrastructure.persistence.subscriber;

import com.sejong.newsletterservice.domain.model.enums.EmailFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataJpaSubscriberRepository extends JpaRepository<SubscriberEntity, Long> {

    @Query("SELECT s FROM SubscriberEntity s LEFT JOIN FETCH s.mailCategories WHERE s.emailFrequency = :emailFrequency")
    List<SubscriberEntity> findByEmailFrequencyWithCategories(@Param("emailFrequency") EmailFrequency emailFrequency);
}
