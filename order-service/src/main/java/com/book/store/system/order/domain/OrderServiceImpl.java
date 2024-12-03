package com.book.store.system.order.domain;

import com.book.store.system.order.domain.enums.OrderStatus;
import com.book.store.system.order.domain.events.OrderCreatedEvent;
import com.book.store.system.order.domain.exceptions.OrderNotFoundException;
import com.book.store.system.order.domain.models.CreateOrderRequest;
import com.book.store.system.order.domain.models.CreateOrderResponse;
import com.book.store.system.order.domain.models.OrderDto;
import com.book.store.system.order.domain.models.OrderSummary;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
class OrderServiceImpl implements OrderService {

    private static final List<String> DELIVERY_ALLOWED_COUNTRIES = List.of("EGYPT", "SA");

    private final OrderRepository orderRepository;
    private final OrderHelper orderHelper;
    private final OrderEventService orderEventService;
    private final OrderEventMapper orderEventMapper;

    @Override
    public CreateOrderResponse createOrder(String userName, CreateOrderRequest createOrderRequest) {
        orderHelper.validateOrder(createOrderRequest);
        OrderEntity orderEntity = OrderMapper.createOrderRequestToOrderEntity(createOrderRequest);
        orderEntity.setUserName(userName);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        log.info("Order is created with order number: {}", savedOrderEntity.getOrderNumber());
        OrderCreatedEvent orderCreatedEvent = orderEventMapper.orderEntityToOrderCreatedEvent(savedOrderEntity);
        orderEventService.save(orderCreatedEvent);
        return new CreateOrderResponse(savedOrderEntity.getOrderNumber());
    }

    @Override
    public void processingNewOrders() {
        List<OrderEntity> orderEntities = orderRepository.findByStatus(OrderStatus.NEW);
        log.info("Found {} new orders to process", orderEntities.size());
        orderEntities.forEach(this::process);
    }

    @Override
    public List<OrderSummary> getOrders(String userName) {
        return orderRepository.findByUserName(userName);
    }

    @Override
    public OrderDto getOrder(String orderNumber, String userName) {
        return orderRepository
                .findByUserNameAndOrderNumber(userName, orderNumber)
                .map(OrderMapper::orderEntityToOrderDto)
                .orElseThrow(() -> OrderNotFoundException.forOrderNumber(orderNumber));
    }

    private void process(OrderEntity order) {
        try {
            if (canBeDelivered(order)) {
                log.info("OrderNumber: {} can be delivered", order.getOrderNumber());
                updateOrderStatus(order.getOrderNumber(), OrderStatus.DELIVERED);
                orderEventService.save(orderEventMapper.orderEntityToOrderDeliveredEvent(order));

            } else {
                log.info("OrderNumber: {} can not be delivered", order.getOrderNumber());
                updateOrderStatus(order.getOrderNumber(), OrderStatus.CANCELLED);
                orderEventService.save(
                        orderEventMapper.orderEntityToOrderCancelledEvent(order, "Can't deliver to the location"));
            }
        } catch (RuntimeException e) {
            log.error("Failed to process Order with orderNumber: {}", order.getOrderNumber(), e);
            updateOrderStatus(order.getOrderNumber(), OrderStatus.ERROR);
            orderEventService.save(orderEventMapper.orderEntityToOrderErrorEvent(order, e.getMessage()));
        }
    }

    private void updateOrderStatus(String orderNumber, OrderStatus orderStatus) {
        OrderEntity orderEntity = orderRepository.findByOrderNumber(orderNumber).orElseThrow();
        orderEntity.setStatus(orderStatus);
        orderRepository.save(orderEntity);
    }

    private boolean canBeDelivered(OrderEntity order) {
        return DELIVERY_ALLOWED_COUNTRIES.contains(
                order.getDeliveryAddress().country().toUpperCase());
    }
}
