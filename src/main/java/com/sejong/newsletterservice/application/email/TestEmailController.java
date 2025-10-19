package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.core.enums.PostType;
import com.sejong.newsletterservice.infrastructure.email.EmailContentBuilder;
import com.sejong.newsletterservice.infrastructure.feign.response.MetaVisitersAllResponse;
import com.sejong.newsletterservice.infrastructure.feign.response.PopularPost;
import java.util.Arrays;
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
        PopularPost newsPost = PopularPost.builder()
                .title("Spring Boot 3.0 새로운 기능들")
                .postType(PostType.NEWS)
                .postId("123")
                .likeCount(150L)
                .build();

        PopularPost projectPost = PopularPost.builder()
                .title("마이크로서비스 아키텍처 구현하기")
                .postType(PostType.PROJECT)
                .postId("456")
                .likeCount(89L)
                .build();

        PopularPost archivePost = PopularPost.builder()
                .title("JPA 성능 최적화 팁")
                .postType(PostType.NEWS)
                .postId("789")
                .likeCount(234L)
                .build();

        MetaVisitersAllResponse response = MetaVisitersAllResponse.builder()
                .popularPosts(Arrays.asList(newsPost, projectPost, archivePost))
                .build();

        String html = emailContentBuilder.buildMostVisitersPostHtml(
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
            PopularPost newsPost = PopularPost.builder()
                    .title("Spring Boot 3.0 새로운 기능들")
                    .postType(PostType.NEWS)
                    .postId("123")
                    .likeCount(150L)
                    .build();

            PopularPost projectPost = PopularPost.builder()
                    .title("마이크로서비스 아키텍처 구현하기")
                    .postType(PostType.PROJECT)
                    .postId("456")
                    .likeCount(89L)
                    .build();

            PopularPost archivePost = PopularPost.builder()
                    .title("JPA 성능 최적화 팁")
                    .postType(PostType.NEWS)
                    .postId("789")
                    .likeCount(234L)
                    .build();

            MetaVisitersAllResponse response = MetaVisitersAllResponse.builder()
                    .popularPosts(Arrays.asList(newsPost, projectPost, archivePost))
                    .build();

            // 실제 이메일 전송 테스트
            newsletterEmailSender.sendMostVisiters(
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
            PopularPost newsPost = PopularPost.builder()
                    .title("Spring Boot 3.0 새로운 기능들")
                    .postType(PostType.NEWS)
                    .postId("123")
                    .likeCount(150L)
                    .build();

            PopularPost projectPost = PopularPost.builder()
                    .title("마이크로서비스 아키텍처 구현하기")
                    .postType(PostType.PROJECT)
                    .postId("456")
                    .likeCount(89L)
                    .build();

            PopularPost archivePost = PopularPost.builder()
                    .title("JPA 성능 최적화 팁")
                    .postType(PostType.NEWS)
                    .postId("789")
                    .likeCount(234L)
                    .build();

            MetaVisitersAllResponse response = MetaVisitersAllResponse.builder()
                    .popularPosts(Arrays.asList(newsPost, projectPost, archivePost))
                    .build();

            // 실제 이메일 전송 테스트
            newsletterEmailSender.sendMostVisiters(
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