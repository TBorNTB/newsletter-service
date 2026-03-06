package com.sejong.newsletterservice.support.email;

import com.sejong.newsletterservice.support.exception.EmailSendException;
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

@Service("verificationSender")
@Slf4j
@RequiredArgsConstructor
public class VerificationEmailService implements VerificationEmailSender {

    private final JavaMailSender mailSender;
    private final EmailContentBuilder emailContentBuilder;

    @Override
    @Async
    @Retryable(value = {MessagingException.class, UnsupportedEncodingException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
    public void send(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(to);
            helper.setFrom("your_email@gmail.com", "SSG 보안동아리");
            helper.setSubject("[SSG 보안동아리] 이메일 인증을 진행해주세요.");
            helper.setText(emailContentBuilder.buildVerificationHtml(code), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendException("메일 전송 중 오류가 발생했습니다.", e);
        } catch (UnsupportedEncodingException e) {
            throw new EmailSendException("보내는 사람 주소 인코딩 오류", e);
        } catch (Exception e) {
            throw new EmailSendException("예상치 못한 오류로 인해 메일 전송 실패", e);
        }
    }
}
