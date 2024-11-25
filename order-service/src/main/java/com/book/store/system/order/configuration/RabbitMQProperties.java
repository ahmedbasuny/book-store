package com.book.store.system.order.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("orders")
public record RabbitMQProperties(
        String orderEventsExchange,
        String newOrdersQueue,
        String deliveredOrdersQueue,
        String cancelledOrdersQueue,
        String errorOrdersQueue) {}
