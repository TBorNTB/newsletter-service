package com.sejong.newsletterservice.infrastructure.persistence.category;

import com.sejong.newsletterservice.domain.model.MailCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMailCategoryRepository extends JpaRepository<MailCategoryEntity, Long> {
}
