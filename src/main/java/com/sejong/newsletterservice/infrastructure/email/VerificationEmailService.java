package com.sejong.newsletterservice.infrastructure.email;

import com.sejong.newsletterservice.application.email.VerificationEmailSender;
import com.sejong.newsletterservice.application.exception.EmailSendException;
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

@Service("verificationSender")
@Slf4j
@RequiredArgsConstructor
public class VerificationEmailService implements VerificationEmailSender {
    //GmailService 확장으로 인해 주석처리
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
    public void send(String to, String code) {
        try {
            log.info("Sending email to " + to);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(to);
            helper.setFrom("your_email@gmail.com", "뉴스레터");
            helper.setSubject("[뉴스레터] 이메일 인증을 진행해주세요.");
            helper.setText(emailContentBuilder.buildVerificationHtml(code) , true);

            mailSender.send(message);
            log.info("Email sent");
        }catch (MessagingException e) {
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

//    @Override
//    @Async
//    public void send(String to, String code) {
//        try {
//            log.info("Sending verification email to " + to);
//            String html = emailContentBuilder.buildVerificationHtml(code);
//            gmailService.sendHtmlEmail(to, "[뉴스레터] 이메일 인증을 진행해주세요.", html);
//            log.info("Email sent");
//        } catch (Exception e) {
//            log.error("이메일 전송 실패", e);
//            throw new RuntimeException("이메일 전송 실패", e);
//        }
//    }

}
