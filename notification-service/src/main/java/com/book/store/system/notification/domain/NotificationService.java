package com.book.store.system.notification.domain;

import com.book.store.system.notification.domain.events.OrderCancelledEvent;
import com.book.store.system.notification.domain.events.OrderCreatedEvent;
import com.book.store.system.notification.domain.events.OrderDeliveredEvent;
import com.book.store.system.notification.domain.events.OrderErrorEvent;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {
    void sendOrderCreatedNotification(OrderCreatedEvent event);

    void sendOrderDeliveredNotification(OrderDeliveredEvent event);

    void sendOrderCancelledNotification(OrderCancelledEvent event);

    void sendOrderErrorEventNotification(OrderErrorEvent event);
}
