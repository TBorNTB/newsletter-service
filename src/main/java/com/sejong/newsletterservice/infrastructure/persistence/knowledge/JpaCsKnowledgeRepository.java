package com.sejong.newsletterservice.infrastructure.persistence.knowledge;

import com.sejong.newsletterservice.domain.model.CsKnowledge;
import com.sejong.newsletterservice.domain.model.MailCategory;
import com.sejong.newsletterservice.domain.model.enums.MailCategoryName;
import com.sejong.newsletterservice.domain.repository.CsKnowledgeRepository;
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
        System.out.println("findUnsentKnowledge called");
        Optional<CsKnowledgeEntity> randomUnsent = repository.findRandomUnsent(categoryName.name(), email);



        if(randomUnsent.isEmpty()) {
            log.info("데이터가 없졍");
        }
        else{
            log.info("데이터가 있엉");
        }
        return randomUnsent.map(CsKnowledgeEntity::toDomain);

    }
}
