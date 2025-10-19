package com.sejong.newsletterservice.infrastructure.email;

import com.sejong.newsletterservice.application.email.NewsletterEmailSender;
import com.sejong.newsletterservice.application.exception.EmailSendException;
import com.sejong.newsletterservice.infrastructure.feign.response.MetaVisitersAllResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNewsletterService implements NewsletterEmailSender {

    //확장성을 고려해 GamilService로 대체
     private final JavaMailSender mailSender;

    //private final GmailService gmailService;
    private final EmailContentBuilder emailContentBuilder;

    @Override
    @Async
    @Retryable(
            value = { MessagingException.class, UnsupportedEncodingException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void send(String to, String subject, String csKnowledgeId) {
        try {
            log.info("Sending email to " + to);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(to);
            helper.setFrom("kkd06155@gmail.com", "뉴스레터");
            helper.setSubject(subject);
            boolean hasKnowledge = !subject.startsWith("<*>");
            helper.setText(emailContentBuilder.buildNewsletterHtml(subject, "http://empty.com", "kkd06144@naver.com",hasKnowledge), true);

            mailSender.send(message);
            log.info("Email sent");
        } catch (MessagingException e) {
            log.error("MessagingException occurred while sending email to {}: {}", to, e.getMessage(), e);
            throw new EmailSendException("메일 전송 중 오류가 발생했습니다.", e);
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException occurred while setting sender address: {}", e.getMessage(), e);
            throw new EmailSendException("보내는 사람 주소 인코딩 오류", e);
        } catch (Exception e) {
            log.error("Unexpected error occurred while sending email to {}: {}", to, e.getMessage(), e);
            throw new EmailSendException("예상치 못한 오류로 인해 메일 전송 실패", e);
        }
    }

    @Override
    @Async
    @Retryable(
            value = { MessagingException.class, UnsupportedEncodingException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void sendMostVisiters(String email, String title, MetaVisitersAllResponse response) {
        try {
            log.info("Sending favorite post email to {}", email);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(email);
            helper.setFrom("kkd06155@gmail.com", "뉴스레터");
            helper.setSubject(title);

            // 이메일 본문 HTML 생성
            String html = emailContentBuilder.buildMostVisitersPostHtml(title, response, email, true);
            helper.setText(html, true);

            mailSender.send(message);
            log.info("Favorite post email sent to {}", email);
        } catch (MessagingException e) {
            log.error("MessagingException occurred while sending favorite post email to {}: {}", email, e.getMessage(), e);
            throw new EmailSendException("인기글 메일 전송 중 오류가 발생했습니다.", e);
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException while setting sender address: {}", e.getMessage(), e);
            throw new EmailSendException("보내는 사람 주소 인코딩 오류", e);
        } catch (Exception e) {
            log.error("Unexpected error occurred while sending favorite post email to {}: {}", email, e.getMessage(), e);
            throw new EmailSendException("예상치 못한 오류로 인해 인기글 메일 전송 실패", e);
        }
    }


//    @Override
//    @Async
//    public void send(String to, String subject) {
//        try {
//            log.info("Sending newsletter email to " + to);
//            boolean hasKnowledge = !subject.startsWith("<*>");
//            String html = emailContentBuilder.buildNewsletterHtml(subject, "http://empty.com", hasKnowledge);
//            gmailService.sendHtmlEmail(to, subject, html);
//            log.info("Email sent");
//        } catch (Exception e) {
//            log.error("뉴스레터 이메일 전송 실패", e);
//            throw new RuntimeException("뉴스레터 이메일 전송 실패", e);
//        }
//    }
}

