package org.example.service;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.GlobalConstant;
import org.example.entity.Order;
import org.example.entity.Ping;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author yuancetian
 */
@Slf4j
@Service
public class OrderService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    private final Map<String, Ping> pings = new ConcurrentHashMap<>();


    public void orderPaidTimeout(String orderNo) {
        //todo 取消订单
    }

    public void addOrder(Order order) {
        long timeout = RandomUtil.randomLong(1L, 100L) * 1000;
        order.setExpire(timeout);
        log.info("添加订单信息 {}", JSON.toJSONString(order));
        orders.put(order.getOrderKey(), order);
        stringRedisTemplate.opsForValue().set(order.getOrderKey(), "1", timeout, TimeUnit.MILLISECONDS);
    }

    public Map<String, Order> getOrders() {
        return orders;
    }

    public Map<String, Ping> getPings() {
        return pings;
    }
}
