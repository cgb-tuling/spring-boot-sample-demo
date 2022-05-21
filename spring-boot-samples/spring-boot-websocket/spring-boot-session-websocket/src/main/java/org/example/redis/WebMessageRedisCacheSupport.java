package org.example.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * MSA平台缓存 Redis操作缺省实现
 */
@Slf4j
public class WebMessageRedisCacheSupport implements WebMessageCacheSupport {
    /**
     * Redis服务器状态
     */
    private static boolean closed = true;

    public static boolean isClosed() {
        return closed;
    }

    public static void setClosed(boolean closed) {
        WebMessageRedisCacheSupport.closed = closed;
    }

    @Autowired
    @Qualifier("jdkRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Boolean delete(String key) {
        if (closed) {
            return false;
        }

        return redisTemplate.delete(key);
    }

    @Override
    public Object get(String key) {
        if (closed) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(String key, Object value) {
        if (closed) {
            return;
        }

        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long time) {
        if (closed) {
            return;
        }

        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Override
    public void clear() {
        if (closed) {
            return;
        }

        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return "OK";
        });
    }

    @Scheduled(fixedDelay = 5000)
    @Override
    public void healthCheck() {
        try {
            if (!closed) {
                String pong = redisTemplate.execute(RedisConnectionCommands::ping);

                closed = !"PONG".equals(pong);
            }
        } catch (Exception e) {
            log.error("Redis health is error, [{}]", e.getMessage());
            closed = true;
        }
    }

    @Override
    public boolean isValid() {
        return !closed;
    }

    @Override
    public Set<String> getKeysLike(String keyLike) {
        if(closed){
            return null;
        }
        return redisTemplate.keys("*" + keyLike + "*");
    }
}
