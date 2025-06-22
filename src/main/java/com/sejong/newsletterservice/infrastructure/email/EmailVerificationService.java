package com.sejong.newsletterservice.infrastructure.email;

import com.sejong.newsletterservice.application.email.EmailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service("verificationSender")
@Slf4j
@RequiredArgsConstructor
public class EmailVerificationService implements EmailSender {

    private final JavaMailSender mailSender;
    private final EmailContentBuilder emailContentBuilder;;

    @Override
    @Async
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
        } catch (MessagingException e ) {
            throw new RuntimeException("이메일 전송 실패", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
