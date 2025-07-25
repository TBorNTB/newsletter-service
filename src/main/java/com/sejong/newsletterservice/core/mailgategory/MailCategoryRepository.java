package com.sejong.newsletterservice.core.mailgategory;

import com.sejong.newsletterservice.core.subscriber.Subscriber;

import java.util.List;

public interface MailCategoryRepository {
    Subscriber saveCategoriesTo(Subscriber subscriber, List<MailCategory> mailCategories);
}
