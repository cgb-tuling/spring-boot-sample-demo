package org.example.controller;

import org.example.constant.GlobalConstant;
import org.example.entity.Order;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author yuancetian
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String setOrderKey(@RequestParam String orderNo) {
        stringRedisTemplate.opsForValue().set(GlobalConstant.RedisPrefixKey.ORDER_PREFIX + orderNo, "1", 1L, TimeUnit.SECONDS);
        return "success";
    }

    @GetMapping("add")
    public void addOrder() {
        CompletableFuture.runAsync(() -> {
            for (int i = 1; i <= 100; i++) {
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
        });

    }
}
