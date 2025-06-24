package com.sejong.newsletterservice.infrastructure.redis;

import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import com.sejong.newsletterservice.infrastructure.redis.dto.SentLogCacheDto;
import com.sejong.newsletterservice.infrastructure.redis.enums.SentLogStatus;
import com.sejong.newsletterservice.infrastructure.sentlog.SentLogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SentLogCacheService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final Duration TTL = Duration.ofMinutes(5);

    private String key(String email) {
        return "newsletter:log:" + email;
    }

    public void save(SentLogCacheDto dto) {
        redisTemplate.opsForValue().set(key(dto.getEmail()), dto, TTL);
    }

    public Optional<SentLogCacheDto> get(String email) {
        Object obj = redisTemplate.opsForValue().get(key(email));
        if (obj instanceof SentLogCacheDto dto) {
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public void updateStatus(String email, SentLogStatus status) {
        get(email).ifPresent(dto -> {
            dto.setStatus(status);
            redisTemplate.opsForValue().set(key(email),dto,TTL);
        });
    }

    public void delete(String email) {
        redisTemplate.delete(key(email));
    }

    public List<SentLogCacheDto> getAllSuccessLogsAndDelete() {
        Set<String> keys = redisTemplate.keys("newsletter:log:*");
        List<SentLogCacheDto> successLogs = new ArrayList<>();
        if(keys == null ) return successLogs;

        for (String key : keys) {
            Object obj = redisTemplate.opsForValue().get(key);
            if (obj instanceof SentLogCacheDto dto && SentLogStatus.SUCCESS.equals(dto.getStatus())) {
                successLogs.add(dto);
                redisTemplate.delete(key);
            }
        }
        return successLogs;
    }
}
