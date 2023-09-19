package com.example.order.event;

import com.example.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventListener {

    private OrderService orderService;

    private KafkaTemplate kafkaTemplate;

    private final static String ORDER_TOPIC_NAME = "order-create";

    public OrderEventListener(KafkaTemplate kafkaTemplate, OrderService orderService) {
        this.orderService = orderService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order-rollback", groupId = "group-01")
    public void stockOrder(String prodNo) {
        log.info("[KAFKA] Get Cancel Order , prodNo : {}", prodNo);
        orderService.cancelOrderFromProdNo(prodNo);
    }

    @EventListener
    public void sendMessage(OrderEvent orderEvent) {
        log.info("Send to Kafka , msg : {}", orderEvent.getOrderNo());
        kafkaTemplate.send(ORDER_TOPIC_NAME, orderEvent.getOrderNo());
    }

}
