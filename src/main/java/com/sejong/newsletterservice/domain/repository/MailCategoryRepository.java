package com.sejong.newsletterservice.domain.repository;

import com.sejong.newsletterservice.domain.model.MailCategory;
import com.sejong.newsletterservice.domain.model.Subscriber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailCategoryRepository {
    Subscriber save(Subscriber subscriber, List<MailCategory> mailCategories);
}
