package com.sejong.newsletterservice.infrastructure.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class EmailContentBuilder {
    private final TemplateEngine templateEngine;

    public String buildNewsletterHtml(String title, String link, boolean hasKnowledge) {
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("link", link);
        context.setVariable("hasKnowledge", hasKnowledge);
        return templateEngine.process("email/newsletter", context);
    }

    public String buildVerificationHtml(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("email/verification", context);
    }
}
