package com.sejong.newsletterservice.infrastructure.knowledge;

import com.sejong.newsletterservice.core.csknowledge.CsKnowledge;
import com.sejong.newsletterservice.core.mailgategory.MailCategory;
import com.sejong.newsletterservice.core.enums.MailCategoryName;
import com.sejong.newsletterservice.core.csknowledge.CsKnowledgeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JpaCsKnowledgeRepository implements CsKnowledgeRepository {

    private final SpringDataJpaCsKnowledgeRepository repository;

    @Override
    public List<CsKnowledge> findAllByMailCategory(MailCategory mailCategory) {
        return repository
                .findAllByMailCategoryName(mailCategory.getMailCategoryName()).stream()
                .map(CsKnowledgeEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<CsKnowledge> findUnsentKnowledge(MailCategoryName categoryName, String email) {
        Optional<CsKnowledgeEntity> randomUnsent = repository.findRandomUnsent(categoryName.name(), email);
        return randomUnsent.map(CsKnowledgeEntity::toDomain);
    }
}
