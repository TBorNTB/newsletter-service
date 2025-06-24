package com.sejong.newsletterservice.infrastructure.email;

import com.sejong.newsletterservice.application.email.NewsletterEmailSender;
import com.sejong.newsletterservice.infrastructure.redis.SentLogCacheService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


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
    public void send(String to, String subject, Long csKnowledgeId) {
        try {
            log.info("Sending email to " + to);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(to);
            helper.setFrom("kkd06155@gmail.com", "뉴스레터");
            helper.setSubject(subject);
            boolean hasKnowledge = !subject.startsWith("<*>");
            helper.setText(emailContentBuilder.buildNewsletterHtml(subject, "http://empty.com", hasKnowledge), true);

            mailSender.send(message);
            log.info("Email sent");
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("이메일 전송 중 예외 발생", e);
            throw new RuntimeException("뉴스레터 이메일 전송 실패", e);
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

