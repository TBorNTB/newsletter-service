package com.sejong.newsletterservice.infrastructure.redis;

import com.sejong.newsletterservice.domain.model.vo.SubscriberRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriberCacheService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final Duration TTL = Duration.ofMinutes(5);

    private String key(String email) {
        return "verify:" + email;
    }

    public void save(SubscriberRequestVO vo) {
        redisTemplate.opsForValue().set(key(vo.email()),vo,TTL);
    }

    public Optional<SubscriberRequestVO> getEmailInfo(String email) {
        Object obj = redisTemplate.opsForValue().get(key(email));
        if (obj instanceof SubscriberRequestVO vo) {
            return Optional.of(vo);
        }
        return Optional.empty();
    }

    public void remove(String email){
        redisTemplate.delete(key(email));
    }
}
