package com.sejong.newsletterservice.infrastructure.subscriber;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataJpaSubscriberRepository extends JpaRepository<SubscriberEntity, Long> {

    @Query("SELECT s FROM SubscriberEntity s LEFT JOIN FETCH s.mailCategories WHERE s.emailFrequency = :emailFrequency")
    List<SubscriberEntity> findByEmailFrequency(@Param("emailFrequency") EmailFrequency emailFrequency);
}
