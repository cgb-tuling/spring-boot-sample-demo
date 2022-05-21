package org.example;

import java.math.BigDecimal;
import java.util.Date;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import org.example.constant.GlobalConstant;
import org.example.entity.Order;
import org.example.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author yuancetian
 */
@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = Application14.class)
public class RedisKeyTest {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private OrderService orderService;

    @Test
    public void setOrderKey() {
        for (int i = 1; i <= 1000; i++) {
            long timeout = RandomUtil.randomLong(1L, 100L) * 10;
            stringRedisTemplate.opsForValue().set(GlobalConstant.RedisPrefixKey.ORDER_PREFIX + i, "1", timeout, TimeUnit.SECONDS);
        }
    }


    @Test
    public void addOrder() {
        for (int i = 1; i <= 1000; i++) {
            Order order = new Order();
            String format = String.format("%03d", i);
            order.setOrderNo("OA" + format);
            order.setOrderKey(GlobalConstant.RedisPrefixKey.ORDER_PREFIX + order.getOrderNo());
            order.setCreateTime(new Date());
            order.setPrice(new BigDecimal("100"));
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            orderService.addOrder(order);
        }
    }
}
