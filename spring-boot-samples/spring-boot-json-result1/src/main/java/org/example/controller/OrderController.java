package org.example.controller;

import org.example.annonation.ResponseResult;
import org.example.domain.Order;
import org.example.result.Result;
import org.example.result.ResultCode;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("{id}")
    public Result<Order> getOrder(@PathVariable("id") Integer id) {
        Order order = orderService.getOrderById(id);
        return new Result<>(ResultCode.SUCCESS, order);
    }

    @GetMapping("/get/{id}")
    public Result<Order> get0rder(@PathVariable("id") Integer id) {
        if (id == null) {
            return Result.failure(ResultCode.PARAM_IS_INVALID);
        }
        Order order = orderService.getOrderById(id);
        return Result.success(order);
    }

    @GetMapping("find")
    @ResponseResult
    public Order find(Integer id){
        if (true){
            throw new RuntimeException("业务异常");
        }
        return orderService.getOrderById(id);
    }
}