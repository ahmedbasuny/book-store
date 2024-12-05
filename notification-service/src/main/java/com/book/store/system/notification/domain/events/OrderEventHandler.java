package com.book.store.system.notification.domain.events;

import com.book.store.system.notification.domain.NotificationService;
import com.book.store.system.notification.domain.OrderEventEntity;
import com.book.store.system.notification.domain.OrderEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventHandler {

    private final NotificationService notificationService;
    private final OrderEventRepository orderEventRepository;

    @RabbitListener(queues = "${notifications.new-orders-queue}")
    void handle(OrderCreatedEvent event) {
        log.info("Received order created event with id: {}", event.getEventId());
        if (checkDuplicateEvents(event)) return;
        notificationService.sendOrderCreatedNotification(event);
        saveReceivedEvent(event);
    }

    @RabbitListener(queues = "${notifications.delivered-orders-queue}")
    void handle(OrderDeliveredEvent event) {
        log.info("Received order delivered event with id: {}", event.getEventId());
        if (checkDuplicateEvents(event)) return;
        notificationService.sendOrderDeliveredNotification(event);
        saveReceivedEvent(event);
    }

    @RabbitListener(queues = "${notifications.cancelled-orders-queue}")
    void handle(OrderCancelledEvent event) {
        log.info("Received order cancelled event with id: {}", event.getEventId());
        if (checkDuplicateEvents(event)) return;
        notificationService.sendOrderCancelledNotification(event);
        saveReceivedEvent(event);
    }

    @RabbitListener(queues = "${notifications.error-orders-queue}")
    void handle(OrderErrorEvent event) {
        log.info("Received order error event with id: {}", event.getEventId());
        if (checkDuplicateEvents(event)) return;
        notificationService.sendOrderErrorEventNotification(event);
        saveReceivedEvent(event);
    }

    private boolean checkDuplicateEvents(OrderEvent event) {
        if (orderEventRepository.existsByEventId(event.getEventId())) {
            log.warn("Received duplicate event with eventId: {}", event.getEventId());
            return true;
        }
        return false;
    }

    private void saveReceivedEvent(OrderEvent event) {
        orderEventRepository.save(
                OrderEventEntity.builder().eventId(event.getEventId()).build());
    }
}
