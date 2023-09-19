package com.example.order.controller;

import com.example.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "order")
    public boolean order(String prodNo) {
        return orderService.orderFromProdNo(prodNo);
    }

    @GetMapping(value = "orders")
    public String orders() {
        return orderService.getOrders();
    }
}
