package com.book.store.system.order.domain;

import com.book.store.system.order.domain.models.CreateOrderRequest;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

class OrderMapper {

    static OrderEntity createOrderRequestToOrderEntity(CreateOrderRequest createOrderRequest) {

        OrderEntity orderEntity = OrderEntity.builder()
                .orderNumber(UUID.randomUUID().toString())
                .status(OrderStatus.NEW)
                .customer(createOrderRequest.customer())
                .deliveryAddress(createOrderRequest.deliveryAddress())
                .build();

        Set<OrderItemEntity> orderItems = new HashSet<>();
        createOrderRequest
                .items()
                .forEach(item -> orderItems.add(OrderItemEntity.builder()
                        .code(item.code())
                        .name(item.name())
                        .price(item.price())
                        .quantity(item.quantity())
                        .order(orderEntity)
                        .build()));

        orderEntity.setItems(orderItems);
        return orderEntity;
    }
}
