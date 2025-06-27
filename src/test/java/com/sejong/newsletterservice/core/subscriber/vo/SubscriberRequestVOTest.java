package com.sejong.newsletterservice.core.subscriber.vo;

import com.sejong.newsletterservice.core.enums.EmailFrequency;
import com.sejong.newsletterservice.core.enums.MailCategoryName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class SubscriberRequestVOTest {

    @Test
    void 정상적으로_생성된다() {
        SubscriberRequestVO vo = new SubscriberRequestVO(
                "test@example.com",
                EmailFrequency.DAILY,
                List.of(MailCategoryName.DIGITAL_FORENSICS),
                "123456"
        );

        assertEquals("test@example.com", vo.email());
        assertEquals(EmailFrequency.DAILY, vo.emailFrequency());
        assertEquals(List.of(MailCategoryName.DIGITAL_FORENSICS), vo.selectedCategories());
        assertEquals("123456", vo.code());
    }

    @Test
    void 이메일이_null이면_예외가_발생한다() {
        IllegalArgumentException 예외 = assertThrows(IllegalArgumentException.class, () ->
                new SubscriberRequestVO(null, EmailFrequency.DAILY, List.of(MailCategoryName.CRYPTOGRAPHY), "123456")
        );
        assertEquals("이메일은 반드시 입력해야 됩니다.", 예외.getMessage());
    }

    @Test
    void 이메일이_빈문자열이면_예외가_발생한다() {
        IllegalArgumentException 예외 = assertThrows(IllegalArgumentException.class, () ->
                new SubscriberRequestVO("   ", EmailFrequency.DAILY, List.of(MailCategoryName.DIGITAL_FORENSICS), "123456")
        );
        assertEquals("이메일은 반드시 입력해야 됩니다.", 예외.getMessage());
    }

    @Test
    void 카테고리리스트가_null이면_예외가_발생한다() {
        IllegalArgumentException 예외 = assertThrows(IllegalArgumentException.class, () ->
                new SubscriberRequestVO("test@example.com", EmailFrequency.DAILY, null, "123456")
        );
        assertEquals("category는 반드시 하나는 선택해야 됩니다.", 예외.getMessage());
    }

    @Test
    void 카테고리리스트가_비어있으면_예외가_발생한다() {
        IllegalArgumentException 예외 = assertThrows(IllegalArgumentException.class, () ->
                new SubscriberRequestVO("test@example.com", EmailFrequency.DAILY, List.of(), "123456")
        );
        assertEquals("category는 반드시 하나는 선택해야 됩니다.", 예외.getMessage());
    }
}