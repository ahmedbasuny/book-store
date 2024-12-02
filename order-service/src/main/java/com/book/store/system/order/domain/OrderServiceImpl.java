package com.book.store.system.order.domain;

import com.book.store.system.order.domain.models.CreateOrderRequest;
import com.book.store.system.order.domain.models.CreateOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderHelper orderHelper;

    @Override
    public CreateOrderResponse createOrder(String userName, CreateOrderRequest createOrderRequest) {
        orderHelper.validateOrder(createOrderRequest);
        OrderEntity orderEntity = OrderMapper.createOrderRequestToOrderEntity(createOrderRequest);
        orderEntity.setUserName(userName);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        log.info("Order is created with order number: {}", savedOrderEntity.getOrderNumber());
        return new CreateOrderResponse(savedOrderEntity.getOrderNumber());
    }
}
