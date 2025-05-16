package vn.lottefinance.pdms_core.service.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class RedisQueueService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void pushToQueue(String id, String key) {
        redisTemplate.opsForList().rightPush(key, id);
    }

    public String popFromQueue(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public void removeFromQueue(String id, String key) {
        redisTemplate.opsForList().remove(key, 0, id);
    }

    private boolean acquireLock(String lockKey, long expireSeconds) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, "LOCKED", Duration.ofSeconds(expireSeconds));
        return Boolean.TRUE.equals(success);
    }

    private void releaseLock(String lockKey) {
        redisTemplate.delete(lockKey);
    }

    public void pushToQueue(String id, String key, Duration ttl) {
        redisTemplate.opsForList().rightPush(key, id);
        redisTemplate.expire(key, ttl); // Set TTL for the whole list
    }

    public List<String> getQueueItems(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

}
