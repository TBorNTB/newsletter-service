package com.sejong.newsletterservice.infrastructure.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMailCategoryRepository extends JpaRepository<MailCategoryEntity, Long> {
}
