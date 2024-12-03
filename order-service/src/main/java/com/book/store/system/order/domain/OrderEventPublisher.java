package com.book.store.system.order.domain;

import com.book.store.system.order.configuration.RabbitMQProperties;
import com.book.store.system.order.domain.events.*;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderEventPublisher implements DomainEventPublisher<OrderEvent> {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;

    @Override
    public void publish(OrderEvent orderEvent) {
        rabbitTemplate.convertAndSend(rabbitMQProperties.orderEventsExchange(), orderEvent.getRoutingKey(), orderEvent);
    }
}
