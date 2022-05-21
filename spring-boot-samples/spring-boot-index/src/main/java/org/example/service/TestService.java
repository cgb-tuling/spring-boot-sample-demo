package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yct
 */
@Service
public class TestService {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;


    public void test1() {
        System.out.println(userService);
    }

    public void test2() {
        System.out.println(orderService);
    }
}
