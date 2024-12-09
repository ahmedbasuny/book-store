package com.book.store.system.notification.events;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

import com.book.store.system.notification.AbstractIntegrationTest;
import com.book.store.system.notification.configuration.RabbitMQProperties;
import com.book.store.system.notification.domain.events.OrderCancelledEvent;
import com.book.store.system.notification.domain.events.OrderCreatedEvent;
import com.book.store.system.notification.domain.events.OrderDeliveredEvent;
import com.book.store.system.notification.domain.events.OrderErrorEvent;
import com.book.store.system.notification.domain.model.Address;
import com.book.store.system.notification.domain.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderEventHandlerTests extends AbstractIntegrationTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RabbitMQProperties rabbitMQProperties;

    Customer customer = new Customer("Ahmed Basuny", "ahmedbasuny13@gmail.com", "01276063525");
    Address address = new Address("addr line 1", null, "Cairo", "Alex", "12345", "Egypt");

    @Test
    void shouldHandleOrderCreatedEvent() {
        String orderNumber = UUID.randomUUID().toString();

        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID().toString(),
                orderNumber,
                Set.of(),
                customer,
                address,
                LocalDateTime.now(),
                null,
                rabbitMQProperties.newOrdersQueue());
        rabbitTemplate.convertAndSend(
                rabbitMQProperties.orderEventsExchange(), rabbitMQProperties.newOrdersQueue(), event);

        await().atMost(30, SECONDS).untilAsserted(() -> {
            verify(notificationService).sendOrderCreatedNotification(any(OrderCreatedEvent.class));
        });
    }

    @Test
    void shouldHandleOrderDeliveredEvent() {
        String orderNumber = UUID.randomUUID().toString();

        OrderDeliveredEvent event = new OrderDeliveredEvent(
                UUID.randomUUID().toString(),
                orderNumber,
                Set.of(),
                customer,
                address,
                LocalDateTime.now(),
                null,
                rabbitMQProperties.newOrdersQueue());
        rabbitTemplate.convertAndSend(
                rabbitMQProperties.orderEventsExchange(), rabbitMQProperties.deliveredOrdersQueue(), event);

        await().atMost(30, SECONDS).untilAsserted(() -> {
            verify(notificationService).sendOrderDeliveredNotification(any(OrderDeliveredEvent.class));
        });
    }

    @Test
    void shouldHandleOrderCancelledEvent() {
        String orderNumber = UUID.randomUUID().toString();

        OrderCancelledEvent event = new OrderCancelledEvent(
                UUID.randomUUID().toString(),
                orderNumber,
                Set.of(),
                customer,
                address,
                LocalDateTime.now(),
                "test cancel reason",
                rabbitMQProperties.cancelledOrdersQueue());
        rabbitTemplate.convertAndSend(
                rabbitMQProperties.orderEventsExchange(), rabbitMQProperties.cancelledOrdersQueue(), event);

        await().atMost(30, SECONDS).untilAsserted(() -> {
            verify(notificationService).sendOrderCancelledNotification(any(OrderCancelledEvent.class));
        });
    }

    @Test
    void shouldHandleOrderErrorEvent() {
        String orderNumber = UUID.randomUUID().toString();

        OrderErrorEvent event = new OrderErrorEvent(
                UUID.randomUUID().toString(),
                orderNumber,
                Set.of(),
                customer,
                address,
                LocalDateTime.now(),
                "test error reason",
                rabbitMQProperties.errorOrdersQueue());
        rabbitTemplate.convertAndSend(
                rabbitMQProperties.orderEventsExchange(), rabbitMQProperties.errorOrdersQueue(), event);

        await().atMost(30, SECONDS).untilAsserted(() -> {
            verify(notificationService).sendOrderErrorEventNotification(any(OrderErrorEvent.class));
        });
    }
}
