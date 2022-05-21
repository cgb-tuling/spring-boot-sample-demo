package org.example.config;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.GlobalConstant;
import org.example.entity.Order;
import org.example.entity.Ping;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import static org.example.constant.GlobalConstant.RedisPrefixKey.ORDER_PREFIX;

@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private OrderService orderService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        log.info("------------------redis key 失效; key = " + expiredKey);
        if (expiredKey.startsWith(ORDER_PREFIX)) {
            Date expireTime = new Date();
            Map<String, Order> map = orderService.getOrders();
            Order order = map.get(expiredKey);
            log.info("order key: [{}] order no: [{}] createTime: [{}] expireTime: [{}] intervalTime {} expire [{}] difference value {}",
                    expiredKey,
                    order.getOrderNo(), DateUtil.format(order.getCreateTime(), "yyyy-MM-dd HH:mm:ss.SSS"),
                    DateUtil.format(expireTime, "yyyy-MM-dd HH:mm:ss.SSS"),
                    DateUtil.between(order.getCreateTime(), expireTime, DateUnit.MS),
                    order.getExpire(), Math.abs(DateUtil.between(order.getCreateTime(), expireTime, DateUnit.MS) - order.getExpire()));
            // 获取订单orderNO
            String orderNo = expiredKey.substring(expiredKey.lastIndexOf(":") + 1);
            // 将待支付的订单改为已取消(超时未支付)
            orderService.orderPaidTimeout(orderNo);
        } else if (expiredKey.startsWith(GlobalConstant.RedisPrefixKey.PING_KEY)) {
            Ping ping = orderService.getPings().get(expiredKey);
            Date date = new Date();
            if (ping != null) {
                log.info("ping createTime {} expireTime {} intervalTime {}", DateUtil.format(ping.getCreateTime(), "yyyy-MM-dd HH:mm:ss.SSS"),
                        DateUtil.format(date, "yyyy-MM-dd HH:mm:ss.SSS"), DateUtil.between(ping.getCreateTime(), date, DateUnit.MS));
            }
        }
    }
}