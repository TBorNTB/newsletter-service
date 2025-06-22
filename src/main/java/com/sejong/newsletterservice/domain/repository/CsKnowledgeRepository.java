package com.sejong.newsletterservice.domain.repository;

import com.sejong.newsletterservice.domain.model.CsKnowledge;
import com.sejong.newsletterservice.domain.model.MailCategory;
import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CsKnowledgeRepository {
    List<CsKnowledge> findAllByMailCategory(MailCategory mailCategory);

    Optional<CsKnowledge> findUnsentKnowledge(MailCategoryName categoryName, String email);
}
