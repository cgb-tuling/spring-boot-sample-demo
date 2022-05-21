package org.example.message;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * redis 发布者
 *
 * @author MSI-NB
 */
@RestController
public class RedisPublisher {

    private final String CHANNEL = "test:message:channel";

    private RedisTemplate<String, String> redisTemplate;

    public RedisPublisher(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/publish")
    public void publish(@RequestParam String message) {
        // 发送消息
        redisTemplate.convertAndSend(CHANNEL, message);
    }

}