package org.example.service;

import org.example.annotation.NeedSetValueFeild;
import org.example.dao.OrderDao;
import org.example.domain.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yct
 */
@Service
public class OrderService {
    @Resource
    OrderDao orderDao;

    @NeedSetValueFeild //被这 个注解修饰的方法一律进 入到我们的切面方法中:
    public List<Order> queryOrder(String customerId){
        return orderDao.query(customerId);
    }
}