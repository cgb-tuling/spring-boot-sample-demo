package org.example.service;

import java.util.Date;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.example.constant.GlobalConstant;
import org.example.entity.Ping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author yuancetian
 */
@Service
public class PingService implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        Ping ping = new Ping();
        ping.setApplicationName("spring-boot-redis");
        ping.setCreateTime(new Date());
        ping.setExpire(5 * 1000);
        orderService.getPings().put(GlobalConstant.RedisPrefixKey.PING_KEY, ping);
        stringRedisTemplate.opsForValue().set(GlobalConstant.RedisPrefixKey.PING_KEY, "1", 5, TimeUnit.SECONDS);
    }

}
