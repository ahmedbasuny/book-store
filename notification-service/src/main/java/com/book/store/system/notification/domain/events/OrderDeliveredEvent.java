package com.book.store.system.notification.domain.events;

import com.book.store.system.notification.domain.enums.OrderEventType;
import com.book.store.system.notification.domain.model.Address;
import com.book.store.system.notification.domain.model.Customer;
import com.book.store.system.notification.domain.model.OrderItem;
import java.time.LocalDateTime;
import java.util.Set;

public class OrderDeliveredEvent extends OrderEvent {
    public OrderDeliveredEvent(
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
                OrderEventType.ORDER_DELIVERED);
    }
}
