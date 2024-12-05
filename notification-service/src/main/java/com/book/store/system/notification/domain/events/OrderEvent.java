package com.book.store.system.notification.domain.events;

import com.book.store.system.notification.domain.enums.OrderEventType;
import com.book.store.system.notification.domain.model.Address;
import com.book.store.system.notification.domain.model.Customer;
import com.book.store.system.notification.domain.model.OrderItem;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class OrderEvent implements DomainEvent {
    private final String eventId;
    private final String orderNumber;
    private final Set<OrderItem> items;
    private final Customer customer;
    private final Address deliveryAddress;
    private final LocalDateTime createdAt;
    private final String reason;
    private final String routingKey;
    private final OrderEventType orderEventType;
}
