package com.sejong.newsletterservice.infrastructure.email;

import com.sejong.newsletterservice.infrastructure.feign.response.ContentResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class EmailContentBuilder {
    private final TemplateEngine templateEngine;

    public String buildNewsletterHtml(String title, String link, String email,boolean hasKnowledge) {
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("link", link);
        context.setVariable("hasKnowledge", hasKnowledge);
        context.setVariable("email", email);
        context.setVariable("date", LocalDateTime.now());
        return templateEngine.process("email/newsletter", context);
    }

    public String buildPostHtml(String title, ContentResponse response, String email, boolean hasKnowledge) {
        Context context = new Context();

        String baseUrl = "https://your-domain/";

        context.setVariable("title", title);
        context.setVariable("link", baseUrl);

        // PostType
        context.setVariable("type", response.getContentType());

        // 각 PostType별 링크와 카운트 설정 (템플릿에서 사용할 수 있도록)
//        context.setVariable("hasKnowledge", hasKnowledge); // ?
        context.setVariable("email", email);
        context.setVariable("title", response.getTitle());
        context.setVariable("content", response.getContent());
        context.setVariable("date", LocalDateTime.now());

        return templateEngine.process("email/newsletter", context);
    }

    public String buildPostsHtml(String title, List<ContentResponse> responses, String email, boolean hasKnowledge) {
        Context context = new Context();

        String baseUrl = "https://your-domain/";

        context.setVariable("title", title);
        context.setVariable("link", baseUrl);
        context.setVariable("email", email);
        context.setVariable("hasKnowledge", hasKnowledge);
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
