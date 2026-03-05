package com.sejong.newsletterservice.support.email;

import com.sejong.newsletterservice.support.feign.response.ContentResponse;
import java.time.LocalDateTime;
import java.util.List;import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class EmailContentBuilder {
    private final TemplateEngine templateEngine;

    public String buildNewsletterHtml(String title, String link, String email, boolean hasKnowledge) {
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("link", link);
        context.setVariable("hasKnowledge", hasKnowledge);
        context.setVariable("email", email);
        context.setVariable("date", LocalDateTime.now());
        return templateEngine.process("email/newsletter", context);
    }

    public String buildPostHtml(String title, ContentResponse response, String email) {
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("link", response.getUrl());
        context.setVariable("email", email);
        context.setVariable("content", response);
        context.setVariable("date", LocalDateTime.now());
        return templateEngine.process("email/newsletter", context);
    }

    public String buildPostsHtml(String title, List<ContentResponse> responses, String email) {
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("email", email);
        context.setVariable("contents", responses);
        context.setVariable("date", LocalDateTime.now());
        return templateEngine.process("email/newsletter", context);
    }

    public String buildVerificationHtml(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("email/verification", context);
    }

}
