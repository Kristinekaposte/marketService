package com.marketService.orderService.business.service;

import com.marketService.orderService.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders();

    Optional<Order> findOrderByOrderNumber(String orderNumber);

    Order placeOrder(Order order);
}
