package org.example.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * redis 消息订阅者
 *
 * @author MSI-NB
 */
@Slf4j
@Service
public class MessageSubscriber {
    private final String CHANNEL = "test:message:channel";

    public MessageSubscriber(RedisTemplate redisTemplate) {
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
        redisConnection.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] bytes) {
                // 收到消息的处理逻辑
                log.info("Receive message : " + message);
            }
        }, CHANNEL.getBytes(StandardCharsets.UTF_8));

    }

}