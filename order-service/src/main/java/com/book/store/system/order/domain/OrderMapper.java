package com.book.store.system.order.domain;

import com.book.store.system.order.domain.enums.OrderStatus;
import com.book.store.system.order.domain.models.CreateOrderRequest;
import com.book.store.system.order.domain.models.OrderDto;
import com.book.store.system.order.domain.models.OrderItem;
import com.book.store.system.order.domain.models.OrderSummary;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public static OrderSummary orderEntityToOrderSummary(OrderEntity orderEntity) {
        return new OrderSummary(orderEntity.getOrderNumber(), orderEntity.getStatus());
    }

    public static OrderDto orderEntityToOrderDto(OrderEntity orderEntity) {
        Set<OrderItem> orderItems = orderEntity.getItems().stream()
                .map(item -> new OrderItem(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());

        return new OrderDto(
                orderEntity.getOrderNumber(),
                orderEntity.getUserName(),
                orderItems,
                orderEntity.getCustomer(),
                orderEntity.getDeliveryAddress(),
                orderEntity.getStatus(),
                orderEntity.getComments(),
                orderEntity.getCreatedAt());
    }
}
