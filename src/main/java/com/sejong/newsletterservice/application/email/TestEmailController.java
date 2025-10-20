package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.infrastructure.email.EmailContentBuilder;
import com.sejong.newsletterservice.infrastructure.feign.response.ContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/email")
@RequiredArgsConstructor
public class TestEmailController {

    private final EmailContentBuilder emailContentBuilder;
    private final NewsletterEmailSender newsletterEmailSender;
    private final NewsletterService newsletterService;

    @GetMapping("/newsletter")
    public String testNewsletterTemplate() {
        // 테스트용 데이터 생성
        ContentResponse response = ContentResponse.builder()
                .id("12345")
                .title("JPA 성능 최적화 팁")
                .category("NEWS")
                .likeCount(234)
                .viewCount(123)
                .build();

        String html = emailContentBuilder.buildPostHtml(
                "🔥 이번 주 인기글!",
                response,
                "test@example.com",
                true
        );

        return html;
    }

    @GetMapping("/send-test")
    public String testSendEmail() {
        try {
            // 테스트용 데이터 생성
            ContentResponse response = ContentResponse.builder()
                    .id("12345")
                    .title("JPA 성능 최적화 팁")
                    .category("NEWS")
                    .likeCount(234)
                    .viewCount(123)
                    .build();

            // 실제 이메일 전송 테스트
            newsletterEmailSender.sendPopularContent(
                    "test@example.com", // 실제 테스트할 이메일 주소로 변경하세요
                    "🔥 이번 주 인기글!",
                    response
            );

            return "이메일 전송 성공!";
        } catch (Exception e) {
            return "이메일 전송 실패: " + e.getMessage();
        }
    }

    @GetMapping("/send-favorite")
    public String testSendFavoritePost() {
        try {
            // 테스트용 데이터 생성
            ContentResponse response = ContentResponse.builder()
                    .id("12345")
                    .title("JPA 성능 최적화 팁")
                    .category("NEWS")
                    .likeCount(234)
                    .viewCount(123)
                    .build();

            // 실제 이메일 전송 테스트
            newsletterEmailSender.sendPopularContent(
                    "test@example.com", // 실제 테스트할 이메일 주소로 변경하세요
                    "🔥 이번 주 인기글!",
                    response
            );

            return "인기글 이메일 전송 성공!";
        } catch (Exception e) {
            return "인기글 이메일 전송 실패: " + e.getMessage();
        }
    }
} 