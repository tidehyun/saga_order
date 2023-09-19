package com.example.order.service;

import com.example.order.event.OrderEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class OrderService {

    private final static Map<String, String> orderMap = new HashMap();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ApplicationEventPublisher applicationEventPublisher;

    public OrderService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public boolean orderFromProdNo(String prodNo) {
        orderMap.put(prodNo, "Order_".concat(prodNo));
        applicationEventPublisher.publishEvent(OrderEvent.builder().orderNo("Order_".concat(prodNo)).build());
        return Boolean.TRUE.booleanValue();
    }

    public boolean cancelOrderFromProdNo(String prodNo) {
        orderMap.remove(StringUtils.replace(prodNo, "Order_", ""));
        return Boolean.TRUE.booleanValue();
    }

    public String getOrders() {
        try {
            return objectMapper.writeValueAsString(orderMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
