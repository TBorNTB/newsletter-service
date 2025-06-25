package com.sejong.newsletterservice.infrastructure.aop;

import com.sejong.newsletterservice.infrastructure.redis.SentLogCacheService;
import com.sejong.newsletterservice.infrastructure.redis.dto.SentLogCacheDto;
import com.sejong.newsletterservice.infrastructure.redis.enums.SentLogStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSendLoggingAspect {

    private final SentLogCacheService sentLogCacheService;

    @Pointcut("execution(* com.sejong.newsletterservice.infrastructure.email.EmailNewsletterService.send(..))")
    public void newsletterSendPointcut() {}

    @Before(value = "newsletterSendPointcut() && args(to,subject,csKnowledgeId)", argNames = "to,subject,csKnowledgeId")
    public void logBefore(String to, String subject, Long csKnowledgeId) {
        //cs지식을 전달할게 없으면 csKnowledgeId가 null이 들어온다.
        //그래서 아래와 같이 null인경우는 로그 저장을 생략하게 구현했다.

        if (csKnowledgeId == null) {
            log.info("[AOP] csKnowledgeId == null → 로그 저장 생략");
            return;
        }

        sentLogCacheService.save(SentLogCacheDto.of(
                to, csKnowledgeId, LocalDateTime.now(), SentLogStatus.PENDING
        ));
        log.info(" [AOP] Redis에 PENDING 로그 저장 - {}", to);
    }

    @AfterReturning(value = "newsletterSendPointcut() && args(to,subject,csKnowledgeId)", argNames = "to,subject,csKnowledgeId")
    public void logSuccess(String to, String subject, Long csKnowledgeId) {
        if (csKnowledgeId == null) return;
        sentLogCacheService.updateStatus(to, SentLogStatus.SUCCESS);
        log.info(" [AOP] Redis 상태 SUCCESS로 변경 - {}", to);
    }

    @AfterThrowing(value = "newsletterSendPointcut() && args(to,subject,csKnowledgeId)", argNames = "to,subject,csKnowledgeId")
    public void logFailure(String to, String subject, Long csKnowledgeId) {
        if (csKnowledgeId == null) return;
        sentLogCacheService.updateStatus(to, SentLogStatus.FAIL);
        log.warn(" [AOP] Redis 상태 FAIL로 변경 - {}", to);
    }
}

