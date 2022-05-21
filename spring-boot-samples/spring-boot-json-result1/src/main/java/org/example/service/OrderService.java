package org.example.service;

import org.example.domain.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    public Order getOrderById(Integer id) {
        Order order = new Order();
        order.setId(id);
        order.setName("手机");
        order.setPrice(BigDecimal.valueOf(3000.0));
        return order;
    }
}
