package com.book.store.system.order.domain;

import com.book.store.system.order.configuration.RabbitMQProperties;
import com.book.store.system.order.domain.events.OrderCancelledEvent;
import com.book.store.system.order.domain.events.OrderCreatedEvent;
import com.book.store.system.order.domain.events.OrderDeliveredEvent;
import com.book.store.system.order.domain.events.OrderErrorEvent;
import com.book.store.system.order.domain.models.OrderItem;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderEventMapper {

    private final RabbitMQProperties rabbitMQProperties;

    public OrderCreatedEvent orderEntityToOrderCreatedEvent(OrderEntity orderEntity) {
        return new OrderCreatedEvent(
                UUID.randomUUID().toString(),
                orderEntity.getOrderNumber(),
                getOrderItems(orderEntity),
                orderEntity.getCustomer(),
                orderEntity.getDeliveryAddress(),
                LocalDateTime.now(),
                null,
                rabbitMQProperties.newOrdersQueue());
    }

    OrderDeliveredEvent orderEntityToOrderDeliveredEvent(OrderEntity orderEntity) {
        return new OrderDeliveredEvent(
                UUID.randomUUID().toString(),
                orderEntity.getOrderNumber(),
                getOrderItems(orderEntity),
                orderEntity.getCustomer(),
                orderEntity.getDeliveryAddress(),
                LocalDateTime.now(),
                null,
                rabbitMQProperties.deliveredOrdersQueue());
    }

    OrderCancelledEvent orderEntityToOrderCancelledEvent(OrderEntity orderEntity, String reason) {
        return new OrderCancelledEvent(
                UUID.randomUUID().toString(),
                orderEntity.getOrderNumber(),
                getOrderItems(orderEntity),
                orderEntity.getCustomer(),
                orderEntity.getDeliveryAddress(),
                LocalDateTime.now(),
                reason,
                rabbitMQProperties.cancelledOrdersQueue());
    }

    OrderErrorEvent orderEntityToOrderErrorEvent(OrderEntity orderEntity, String reason) {
        return new OrderErrorEvent(
                UUID.randomUUID().toString(),
                orderEntity.getOrderNumber(),
                getOrderItems(orderEntity),
                orderEntity.getCustomer(),
                orderEntity.getDeliveryAddress(),
                LocalDateTime.now(),
                reason,
                rabbitMQProperties.errorOrdersQueue());
    }

    private Set<OrderItem> getOrderItems(OrderEntity orderEntity) {
        return orderEntity.getItems().stream()
                .map(orderItemEntity -> new OrderItem(
                        orderItemEntity.getCode(),
                        orderItemEntity.getName(),
                        orderItemEntity.getPrice(),
                        orderItemEntity.getQuantity()))
                .collect(Collectors.toSet());
    }
}
