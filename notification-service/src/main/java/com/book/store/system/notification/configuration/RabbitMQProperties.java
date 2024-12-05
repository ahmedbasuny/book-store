package com.book.store.system.notification.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("notifications")
public record RabbitMQProperties(
        String orderEventsExchange,
        String newOrdersQueue,
        String deliveredOrdersQueue,
        String cancelledOrdersQueue,
        String errorOrdersQueue) {}
