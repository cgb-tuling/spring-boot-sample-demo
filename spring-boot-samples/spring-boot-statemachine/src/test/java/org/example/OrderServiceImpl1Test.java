package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.statemachine.SpringBootStatemachineApplication;
import org.example.statemachine.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = SpringBootStatemachineApplication.class)
public class OrderServiceImpl1Test {

    @Autowired
    private OrderService orderService;

    @Test
    public void testMultThread() {
        orderService.creat();
        orderService.creat();

        orderService.pay(1);

        new Thread(() -> {
            orderService.deliver(1);
            orderService.receive(1);
        }).start();

        orderService.pay(2);
        orderService.deliver(2);
        orderService.receive(2);

        System.out.println(orderService.getOrders());
    }
}