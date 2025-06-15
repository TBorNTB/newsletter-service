package com.sejong.newsletterservice.infrastructure.persistence.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaMailCategoryRepository {
    private final SpringDataJpaMailCategoryRepository mailCategoryRepository;

}
