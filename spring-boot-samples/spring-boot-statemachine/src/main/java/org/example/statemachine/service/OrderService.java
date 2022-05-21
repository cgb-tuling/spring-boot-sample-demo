package org.example.statemachine.service;

import org.example.statemachine.model.Order;

import java.util.Map;

public interface OrderService {
    public Order creat();

    public Order pay(int id);

    public Order deliver(int id);

    public Order receive(int id);

    public Map<Integer, Order> getOrders();
}
