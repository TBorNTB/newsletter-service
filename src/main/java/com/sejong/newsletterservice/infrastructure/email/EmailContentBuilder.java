package com.sejong.newsletterservice.infrastructure.email;

import com.sejong.newsletterservice.infrastructure.feign.response.MetaVisitersAllResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

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

    public String buildMostVisitersPostHtml(String title, MetaVisitersAllResponse response, String email, boolean hasKnowledge) {
        Context context = new Context();

        String baseUrl = "https://your-domain/";

        context.setVariable("title", title);
        context.setVariable("link", baseUrl);

        // 각 PostType별 인기글 정보 설정
        context.setVariable("newsPost", response.getNewsPost());
        context.setVariable("projectPost", response.getProjectPost());
        context.setVariable("archivePost", response.getArchivePost());

        // 각 PostType별 링크와 카운트 설정 (템플릿에서 사용할 수 있도록)
        context.setVariable("hasKnowledge", hasKnowledge);
        context.setVariable("email", email);
        context.setVariable("date", LocalDateTime.now());

        return templateEngine.process("email/newsletter", context);
    }



    public String buildVerificationHtml(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("email/verification", context);
    }
}
