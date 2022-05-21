package com.itmuch.lock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;


/**
 * @author MSI-NB
 */
@Configuration
public class RedisLock {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 获取锁的超时时间
     */
    private long timeout = 100;


    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 加锁，无阻塞
     *
     * @param key        锁
     * @param value      值
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public Boolean tryLock(String key, String value, long expireTime) {

        long start = System.currentTimeMillis();
        //在一定时间内获取锁，超时则返回错误
        for (; ; ) {
            //Set命令返回OK，则证明获取锁成功
            Boolean ret = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
            if (ret != null && ret) {
                return true;
            }
            //否则循环等待，在timeout时间内仍未获取到锁，则获取失败
            long end = System.currentTimeMillis() - start;
            if (end >= timeout) {
                return false;
            }
        }
    }


    /**
     * 解锁
     */
    public Boolean unlock(String key, String value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Object result = redisTemplate.execute(redisScript, Collections.singletonList(key), value);
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
}