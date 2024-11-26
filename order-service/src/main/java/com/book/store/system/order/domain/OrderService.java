package com.book.store.system.order.domain;

import com.book.store.system.order.domain.dtos.CreateOrderRequest;
import com.book.store.system.order.domain.dtos.CreateOrderResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    CreateOrderResponse createOrder(String userName, @Valid CreateOrderRequest createOrderRequest);
}
