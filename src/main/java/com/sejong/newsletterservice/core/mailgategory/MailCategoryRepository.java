package com.sejong.newsletterservice.core.mailgategory;

import com.sejong.newsletterservice.core.subscriber.Subscriber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailCategoryRepository {
    Subscriber saveCategoriesTo(Subscriber subscriber, List<MailCategory> mailCategories);
}
