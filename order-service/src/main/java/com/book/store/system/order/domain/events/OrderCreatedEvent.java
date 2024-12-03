package com.book.store.system.order.domain.events;

import com.book.store.system.order.domain.enums.OrderEventType;
import com.book.store.system.order.domain.models.Address;
import com.book.store.system.order.domain.models.Customer;
import com.book.store.system.order.domain.models.OrderItem;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;

@Getter
public class OrderCreatedEvent extends OrderEvent {

    public OrderCreatedEvent(
            String eventId,
            String orderNumber,
            Set<OrderItem> items,
            Customer customer,
            Address deliveryAddress,
            LocalDateTime createdAt,
            String reason,
            String routingKey) {
        super(
                eventId,
                orderNumber,
                items,
                customer,
                deliveryAddress,
                createdAt,
                reason,
                routingKey,
                OrderEventType.ORDER_CREATED);
    }
}