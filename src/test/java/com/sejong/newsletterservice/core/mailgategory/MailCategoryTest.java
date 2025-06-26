package com.sejong.newsletterservice.core.mailgategory;

import com.sejong.newsletterservice.core.enums.MailCategoryName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MailCategoryTest {

    @DisplayName("MailCategory의 정적 팩터리 메서드를 테스트 한다")
    @Test
    void MailCategory의_정적_팩터리메서드는_정상_작동한다() {
        // when
        MailCategory mailCategory = MailCategory.of(MailCategoryName.CRYPTOGRAPHY);

        // then
        assertNotNull(mailCategory);
        assertThat(mailCategory.getMailCategoryName()).isEqualTo(MailCategoryName.CRYPTOGRAPHY);
    }
}
