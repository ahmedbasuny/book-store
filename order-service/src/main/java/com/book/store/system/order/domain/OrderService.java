package com.book.store.system.order.domain;

import com.book.store.system.order.domain.models.CreateOrderRequest;
import com.book.store.system.order.domain.models.CreateOrderResponse;
import com.book.store.system.order.domain.models.OrderDto;
import com.book.store.system.order.domain.models.OrderSummary;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    CreateOrderResponse createOrder(String userName, @Valid CreateOrderRequest createOrderRequest);

    void processingNewOrders();

    List<OrderSummary> getOrders(String userName);

    OrderDto getOrder(String orderNumber, String userName);
}
