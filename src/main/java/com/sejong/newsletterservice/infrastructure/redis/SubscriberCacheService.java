package com.sejong.newsletterservice.infrastructure.redis;

import com.sejong.newsletterservice.core.subscriber.vo.SubscriberRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriberCacheService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final Duration TTL = Duration.ofMinutes(10);

    private String key(String email) {
        return "verify:" + email;
    }
    private String cancelKey(String email) {
        return "cancel:" + email;
    }

    private String updateKey(String email) {
        return "update:" + email;
    }

    public void save(SubscriberRequestVO vo) {
        redisTemplate.opsForValue().set(key(vo.email()),vo,TTL);
    }

    public void saveUpdate(SubscriberRequestVO vo) {
        redisTemplate.opsForValue().set(updateKey(vo.email()), vo, TTL);
    }

    public void save(String email , String code) {
        redisTemplate.opsForValue().set(cancelKey(email),code,TTL);
    }

    public Optional<SubscriberRequestVO> getEmailInfo(String email) {
        Object obj = redisTemplate.opsForValue().get(key(email));
        if (obj instanceof SubscriberRequestVO vo) {
            return Optional.of(vo);
        }
        return Optional.empty();
    }

    public Optional<SubscriberRequestVO> getUpdateInfo(String email) {
        Object obj = redisTemplate.opsForValue().get(updateKey(email));
        if (obj instanceof SubscriberRequestVO vo) {
            return Optional.of(vo);
        }
        return Optional.empty();
    }

    public Optional<String> getEmailCode(String email) {
        Object obj = redisTemplate.opsForValue().get(cancelKey(email));
        if (obj instanceof String code) {
            return Optional.of(code);
        }
        return Optional.empty();
    }

    public void remove(String email){
        redisTemplate.delete(key(email));
    }

    public void removeUpdate(String email) {
        redisTemplate.delete(updateKey(email));
    }
}
