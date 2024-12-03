package com.book.store.system.order.domain;

import com.book.store.system.order.domain.enums.OrderEventType;
import com.book.store.system.order.domain.events.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrderEventServiceImpl implements OrderEventService {

    public static final Map<OrderEventType, Class<? extends OrderEvent>> EVENT_TYPE_CLASS_MAP = Map.of(
            OrderEventType.ORDER_CREATED, OrderCreatedEvent.class,
            OrderEventType.ORDER_DELIVERED, OrderDeliveredEvent.class,
            OrderEventType.ORDER_CANCELLED, OrderCancelledEvent.class,
            OrderEventType.ORDER_PROCESSING_FAILED, OrderErrorEvent.class);
    private final OrderEventRepository orderEventRepository;
    private final OrderEventPublisher orderEventPublisher;
    private final ObjectMapper objectMapper;

    @Override
    public void save(OrderEvent event) {
        OrderEventEntity orderEvent = new OrderEventEntity();
        orderEvent.setEventId(event.getEventId());
        orderEvent.setEventType(event.getOrderEventType());
        orderEvent.setOrderNumber(event.getOrderNumber());
        orderEvent.setCreatedAt(event.getCreatedAt());
        orderEvent.setPayload(toJsonPayload(event));
        this.orderEventRepository.save(orderEvent);
    }

    @Override
    public void publishOrderEvents() {
        Sort sort = Sort.by("createdAt").ascending();
        List<OrderEventEntity> events = orderEventRepository.findAll(sort);
        log.info("Found {} Order Events to be published", events.size());
        for (OrderEventEntity event : events) {
            this.publishEvent(event);
            orderEventRepository.delete(event);
        }
    }

    //    private void publishEvent(OrderEventEntity event) {
    //        OrderEventType eventType = event.getEventType();
    //        switch (eventType) {
    //            case ORDER_CREATED:
    //                OrderCreatedEvent orderCreatedEvent = fromJsonPayload(event.getPayload(),
    // OrderCreatedEvent.class);
    //                orderEventPublisher.publish(orderCreatedEvent);
    //                break;
    //            case ORDER_DELIVERED:
    //                OrderDeliveredEvent orderDeliveredEvent =
    //                        fromJsonPayload(event.getPayload(), OrderDeliveredEvent.class);
    //                orderEventPublisher.publish(orderDeliveredEvent);
    //                break;
    //            case ORDER_CANCELLED:
    //                OrderCancelledEvent orderCancelledEvent =
    //                        fromJsonPayload(event.getPayload(), OrderCancelledEvent.class);
    //                orderEventPublisher.publish(orderCancelledEvent);
    //                break;
    //            case ORDER_PROCESSING_FAILED:
    //                OrderErrorEvent orderErrorEvent = fromJsonPayload(event.getPayload(), OrderErrorEvent.class);
    //                orderEventPublisher.publish(orderErrorEvent);
    //                break;
    //            default:
    //                log.warn("Unsupported OrderEventType: {}", eventType);
    //        }
    //    }

    private void publishEvent(OrderEventEntity event) {
        Class<? extends OrderEvent> eventClass = EVENT_TYPE_CLASS_MAP.get(event.getEventType());
        if (eventClass == null) {
            log.error("Unsupported OrderEventType: {}", event.getEventType());
            return;
        }
        try {
            OrderEvent orderEvent = fromJsonPayload(event.getPayload(), eventClass);
            orderEventPublisher.publish(orderEvent);
        } catch (Exception e) {
            log.error("Failed to publish event for type: {}", event.getEventType(), e);
        }
    }

    private String toJsonPayload(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T fromJsonPayload(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
