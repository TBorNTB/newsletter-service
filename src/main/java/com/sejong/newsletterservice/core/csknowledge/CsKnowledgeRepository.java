package com.sejong.newsletterservice.core.csknowledge;

import com.sejong.newsletterservice.core.mailgategory.MailCategory;
import com.sejong.newsletterservice.core.enums.MailCategoryName;

import java.util.List;
import java.util.Optional;

public interface CsKnowledgeRepository {
    List<CsKnowledge> findAllByMailCategory(MailCategory mailCategory);

    Optional<CsKnowledge> findUnsentKnowledge(MailCategoryName categoryName, String email);
}
